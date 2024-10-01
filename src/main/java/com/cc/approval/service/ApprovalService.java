package com.cc.approval.service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.ApprovalLine;
import com.cc.approval.domain.ApprovalLineDto;
import com.cc.approval.domain.TemporaryStorage;
import com.cc.approval.domain.TemporaryStorageDto;
import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalLineRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.approval.repository.TemporaryStorageRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;



@Service
@Transactional
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	private final ApprFormRepository apprFormRepository;
	private final EmployeeRepository employeeRepository;
	private final TemporaryStorageRepository temporaryStorageRepository;
	private final ApprovalLineRepository approvalLineRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@Autowired
	public ApprovalService(ApprovalRepository approvalRepository,ApprFormRepository apprFormRepository,
			EmployeeRepository employeeRepository,TemporaryStorageRepository temporaryStorageRepository,
			ApprovalLineRepository approvalLineRepository) {
		
		this.approvalRepository = approvalRepository;
		this.apprFormRepository = apprFormRepository;
		this.employeeRepository = employeeRepository;
		this.temporaryStorageRepository = temporaryStorageRepository;
		this.approvalLineRepository = approvalLineRepository;
	}
	
	
	public Approval getDraftInfoOne(ApprovalDto dto) {
		
		// 세션
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUserAccount = authentication.getName();
	    
	    Employee employee = employeeRepository.findByempAccount(loggedInUserAccount);
		
	    String groupName = employeeService.getUserTeamName(loggedInUserAccount);
	    
	    dto.setAppr_writer_code(employee.getEmpCode());
	    dto.setAppr_writer_name(employee.getEmpName());
	    
		// 기안서 등록
		Long apprWriter = dto.getAppr_writer_code();
		
		Employee emp = employeeRepository.findByempCode(apprWriter);
		
		
		ApprForm apprFo = apprFormRepository.findByapprFormNo(dto.getAppr_form_no());
		
		String documentNumber = generateDocumentNumber(groupName);
		
		Approval approval = Approval.builder()
				.apprNo(dto.getAppr_no())
				.apprTitle(dto.getAppr_title())
				.apprContent(dto.getAppr_content())
				.apprWriterName(emp.getEmpName())
				.apprHoliStart(dto.getAppr_holi_start())
				.apprHoliEnd(dto.getAppr_holi_end())
				.apprHoliUseCount(dto.getAppr_holi_use_count())
				.apprForm(apprFo)
				.employee(emp)
				.docuNo(documentNumber)
				.apprState(dto.getAppr_state() != null ? dto.getAppr_state() : "S")
				.isDeleted(dto.getIs_deleted() != null ? dto.getIs_deleted() : "N")
				.build();
		
		
		return approval;
		
		
	}
	
		// 문서번호 생성하여 가져오기
		public String generateDocumentNumber(String teamName) {
			String currentYear = String.valueOf(Year.now().getValue());
			        
			// 팀명과 연도를 기준으로 가장 최근에 생성된 문서번호 가져오기
		    List<Approval> approvals = approvalRepository.findTop1ByTeamNameAndYearOrderByDocuNoDesc(teamName, currentYear);

	        // 마지막 문서번호에서 카운트를 추출
	        int nextDocuNo = 1;  // 초기값
	        if (!approvals.isEmpty()) {
	            String lastDocuNo = approvals.get(0).getDocuNo();
	            String[] parts = lastDocuNo.split("-");
	            
	            if (parts.length == 3 && parts[0].equalsIgnoreCase(teamName)&& parts[1].equals(currentYear)) {
	                nextDocuNo = Integer.parseInt(parts[2]) + 1;  // 카운트를 1 증가
	                System.out.println("Invalid document number format: " + nextDocuNo);
	            }
	        }

	        // 최종 문서번호 생성
	        String generatedDocNo = String.format("%s-%s-%05d", teamName, currentYear, nextDocuNo);
	        System.out.println("Generated Document Number: " + generatedDocNo);
	        return generatedDocNo;
			
		}

		@Transactional
		public Approval saveApproval(ApprovalDto approvalDto) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			String groupName = employeeService.getUserTeamName(username);
			
			// 로그인한 사용자 정보로 Employee 조회
		    Employee employee = employeeRepository.findByempAccount(username);
		    if (employee == null) {
		        throw new IllegalArgumentException("Invalid employee account: " + username);
		    }
			
			// 기안 작성자 이름 설정
		    approvalDto.setAppr_writer_name(employee.getEmpName());
		    approvalDto.setAppr_writer_code(employee.getEmpCode());
		    approvalDto.setEmp_account(username);
			
	        // 문서번호 생성
	        String documentNumber = generateDocumentNumber(groupName);
	        System.out.println("Generated Document Number: " + documentNumber);
	        
	        // ApprForm 조회
	        ApprForm apprForm = apprFormRepository.findByapprFormNo(approvalDto.getAppr_form_no());


	        // Approval 엔티티 생성 및 문서번호 저장
	        Approval approval = Approval.builder()
	        		.employee(employee)
	                .apprTitle(approvalDto.getAppr_title())
	                .apprContent(approvalDto.getAppr_content())
	                .apprWriterName(approvalDto.getAppr_writer_name())
	                .apprHoliStart(approvalDto.getAppr_holi_start())
	                .apprHoliEnd(approvalDto.getAppr_holi_end())
	                .apprHoliUseCount(approvalDto.getAppr_holi_use_count())
	                .apprState(approvalDto.getAppr_state() != null ? approvalDto.getAppr_state() : "S")
	                .docuNo(documentNumber)  // 생성된 문서번호 저장
	                .apprForm(apprForm)
	                .build();
	        
	        System.out.println("Approval Object: " + approval);
	        
	        // 문서번호가 null이면 에러 로그 출력
	        if (approval.getDocuNo() == null) {
	            throw new RuntimeException("문서번호 없음");
	        }
	        
	        return approvalRepository.save(approval);  // DB에 저장
	    }
	
	// 기안일, 작성자명 가져오기
	public ApprovalDto getDataInfo(ApprovalDto approvalDto) {
		// 세션
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUserAccount = authentication.getName();
	    Employee employee = employeeRepository.findByempAccount(loggedInUserAccount);
	    approvalDto.setAppr_writer_code(employee.getEmpCode());
	    approvalDto.setAppr_writer_name(employee.getEmpName());
		ApprovalDto dto = ApprovalDto.builder()
				.draft_day(approvalDto.getDraft_day())
				.appr_writer_name(approvalDto.getAppr_writer_name()) 
				.build();
				
		return dto;
	}

	
	// 전자결재 메인 결재대기리스트 조회
	public List<ApprovalDto> getAllApprovals(int size) {
		// 현재 로그인한 사용자의 ID 가져오기
	    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    // 페이지 크기를 동적으로 받아서 처리 (예: 5개 또는 10개)
	    Pageable pageable = PageRequest.of(0, size); 
	    
	    // 레포지토리에서 내림차순으로 정렬된 상위 5개의 데이터 조회
        List<Approval> apprList = approvalRepository.findByEmployeeAccountOrderByDraftDayDesc(currentUserId, pageable).getContent();
        
        // Approval 엔티티를 ApprovalDto로 변환
        return apprList.stream()
                       .map(approval -> new ApprovalDto().toDto(approval))
                       .collect(Collectors.toList());
    }
	
	
	// 기안서 상세 조회
	public ApprovalDto selectapprovalOne(Long appr_no) {
		Approval approval = approvalRepository.findByApprNo(appr_no);
		System.out.println("서비스 appr_no"+approval);
		return new ApprovalDto().toDto(approval);
	}
	
	
	// 기안서 임시저장
	public boolean updateApprWithEmpCode(TemporaryStorageDto dto) {
	    // 로그인된 사용자의 memId(empAccount)를 통해 empCode 가져오기
	    String memId = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인된 사용자 ID

	    
	    Employee employee = employeeRepository.findByempAccount(memId);

	    
	    if (employee == null) {
	        return false;  // 사원 정보가 없으면 false 반환
	    }
	    
	    dto.setEmp_code(employee.getEmpCode());  // DTO에 empCode 설정
	    
	    // 임시 저장 로직 호출 (tem_no 사용)
	    return updateAppr(dto) != null;
	    
	}
	
	
	
	public TemporaryStorage updateAppr(TemporaryStorageDto dto) {
		// tem_no로 임시 저장된 데이터 조회
	    TemporaryStorage temp = temporaryStorageRepository.findByTemNo(dto.getTem_no());
		
	   

	    if (temp == null) {
	        // Builder를 사용하여 객체 생성
	        temp = TemporaryStorage.builder()
	                .apprTitle(dto.getAppr_title())
	                .apprContent(dto.getAppr_content())
	                .apprHoliStart(dto.getAppr_holi_start())
		    		.apprHoliEnd(dto.getAppr_holi_end())
		    		.apprHoliUseCount(dto.getAppr_holi_use_count())
	                .build();
	    } else {
	        // 기존 객체 업데이트
	        temp.setApprTitle(dto.getAppr_title());
	        temp.setApprContent(dto.getAppr_content());
	        temp.setApprHoliStart(dto.getAppr_holi_start());
	        temp.setApprHoliEnd(dto.getAppr_holi_end());
	        temp.setApprHoliUseCount(dto.getAppr_holi_use_count());
	    }
	    
	   

	    // Employee 및 ApprForm 객체 조회
	    Employee employee = employeeRepository.findByempCode(dto.getEmp_code());
	    ApprForm apprForm = apprFormRepository.findByapprFormNo(dto.getAppr_form_no());
	    
	   
	    
	    // 엔티티 변환 후 저장
	    temp.setEmployee(employee);
	    temp.setApprForm(apprForm);
	    
	   

	    return temporaryStorageRepository.save(temp);
		
	}


	public TemporaryStorageDto selectTemporaryStorageOne(Long tem_no) {
		TemporaryStorage temp = temporaryStorageRepository.findByTemNo(tem_no); // tem_no로 조회
	    return TemporaryStorageDto.builder()
	    		.tem_no(temp.getTemNo())
	            .appr_title(temp.getApprTitle())
	            .appr_content(temp.getApprContent())
	            .appr_holi_start(temp.getApprHoliStart())	     
	            .appr_holi_end(temp.getApprHoliEnd())
	            .build();
	}
	
	// 임시저장함 데이터 가져오기
	public List<TemporaryStorageDto> getAllTemporaryStorage(int size){
		// 현재 로그인한 사용자의 ID 가져오기
	    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    Pageable pageable = PageRequest.of(0, size);
	    
	    // 레포지토리에서 내림차순으로 정렬된 상위 5개의 데이터 조회
        List<TemporaryStorage> tempList = temporaryStorageRepository.findTop5ByEmployeeEmpAccountOrderByTemNoDesc(currentUserId, pageable).getContent();

        // Approval 엔티티를 ApprovalDto로 변환
        return tempList.stream()
                       .map(temporaryStorage -> new TemporaryStorageDto().toDto(temporaryStorage))
                       .collect(Collectors.toList());
	}
	
	// 임시저장함 상세 조회
	public TemporaryStorageDto selecttemproaryOne(Long tem_no) {
		TemporaryStorage temporaryStorage = temporaryStorageRepository.findByTemNo(tem_no);
		return new TemporaryStorageDto().toDto(temporaryStorage);
	}
	
	// 임시저장함 삭제
	public int deleteTempStorage(Long tem_no) {
		int result = 0;
		try {
			temporaryStorageRepository.deleteById(tem_no);
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 결재문서함 데이터 리스트
		public List<ApprovalDto> getPendingApprovalDtosForCurrentUser(int size) {
		    // 현재 로그인한 사용자의 emp_code 가져오기
			String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
		    Employee currentUser = employeeRepository.findByempAccount(currentUserId);
		     
		    // 페이지 요청 생성 (page와 size를 받아서 처리)
		    Pageable pageable = PageRequest.of(0, size);

		    // 결재 상태가 'S'이고 현재 사용자가 결재자로 등록된 문서 조회
		    Page<Approval> pendingApprovals = approvalLineRepository.findPendingApprovalsForCurrentUser(currentUser.getEmpCode(), pageable);
		    
		    // 추가 로그 출력
		    System.out.println("결재 대기 문서 개수: " + pendingApprovals.getTotalElements());

		    pendingApprovals.forEach(approval -> {
		        System.out.println("결재 문서 번호: " + approval.getApprNo());
		    });

		    // Approval 엔티티를 ApprovalDto로 변환하여 반환
		    return pendingApprovals.getContent().stream()
		        .map(approval -> new ApprovalDto().toDto(approval))
		        .collect(Collectors.toList());
		}

		
		// 결재문서함 상세조회
		public List<ApprovalLineDto> getApprovalLinesByApprNo(Long apprNo) {
		    List<ApprovalLine> approvalLines = approvalLineRepository.findByApproval_ApprNo(apprNo);
		    
		    if (approvalLines.isEmpty()) {
		        throw new EntityNotFoundException("결재선을 찾을 수 없습니다. apprNo: " + apprNo);
		    }
		    
		    // ApprovalLine을 ApprovalLineDto로 변환하여 리스트로 반환
		    return approvalLines.stream()
		                        .map(approvalLine -> new ApprovalLineDto().toDto(approvalLine))
		                        .collect(Collectors.toList());
		}
		
		public ApprovalDto selectapprStorageOne(Long apprNo) {
		    // appr_no를 통해 결재 문서 정보 조회
		    Approval approval = approvalRepository.findById(apprNo)
		        .orElseThrow(() -> new EntityNotFoundException("결재 문서를 찾을 수 없습니다. ID: " + apprNo));
		    
		    // Approval을 ApprovalDto로 변환
		    return new ApprovalDto().toDto(approval);
		}

		public ApprovalLineDto getApprovalLineWithEmployee(Long apprLineId) {
		    Object[] result = approvalLineRepository.findApprovalLineWithEmployeeByApprLineId(apprLineId)
		        .orElseThrow(() -> new EntityNotFoundException("결재선 정보를 찾을 수 없습니다."));

		    ApprovalLine approvalLine = (ApprovalLine) result[0];
		    Employee employee = (Employee) result[1];

		    ApprovalLineDto dto = new ApprovalLineDto();
		    dto.setAppr_line_id(approvalLine.getApprLineId());
		    dto.setAppr_no(approvalLine.getApproval().getApprNo());
		    dto.setAppr_writer_name(approvalLine.getEmployee().getEmpName()); // 결재자 이름 설정
		    return dto;
		}
		
		// 결재문서함 formNo 값 가져오기
		public Optional<Long> getApprFormNoByApprNo(Long apprNo) {
		    List<Object[]> results = approvalRepository.findApprovalLineWithFormNo(apprNo);

		    // 결과가 비어 있지 않고 첫 번째 결과가 null이 아닐 때 처리
		    if (results != null && !results.isEmpty()) {
		        Object[] result = results.get(0); // 첫 번째 결과 가져오기
		        
		        // 결과가 null이 아니고 두 번째 항목이 null이 아닌지 체크 후 캐스팅
		        if (result != null && result.length > 1 && result[1] != null) {
		            Long formNo = (Long) result[1];   // formNo는 두 번째 항목에 위치
		            return Optional.of(formNo);
		        }
		    }

		    // 결과가 없거나 formNo가 없으면 Optional.empty() 반환
		    return Optional.empty();
		}
		
		// apprNo를 통해 Approval 엔티티 조회
	    public Approval findByApprNo(Long apprNo) {
	        // ApprovalRepository의 findByApprNo 메서드 호출
	        return approvalRepository.findByApprNo(apprNo);
	               
	    }
	    
	    // 1차 결재 후에 2차 결재자에게 결재문서 확인
	  		public Page<Approval> findPendingApprovals(Long empCode, int apprOrder, Pageable pageable) {
	  			System.out.println("empCode: " + empCode + ", apprOrder: " + apprOrder); 
	  	        if (apprOrder == 1) {
	  	            // 1차 결재자가 대기 중인 문서 조회
	  	            return approvalLineRepository.findPendingApprovalsForFirstApprover(empCode, pageable);
	  	        } else if (apprOrder == 2) {
	  	            // 2차 결재자가 대기 중인 문서 조회
	  	            return approvalLineRepository.findPendingApprovalsForSecondApprover(empCode, pageable);
	  	        } else {
	  	            throw new IllegalArgumentException("Invalid approval order");
	  	        }
	  	    }
	  		
	  		

	    // 결재 상태 변경
	 		public void approveDocument(Long apprNo, int apprOrder) {
	 		// 로그 추가
	 		    System.out.println("결재 번호: " + apprNo + ", 결재 순서: " + apprOrder);

	 		    ApprovalLine approvalLine = approvalLineRepository.findByApprovalApprNoAndApprOrder(apprNo, apprOrder);
	 		    if (approvalLine != null) {
	 		        System.out.println("현재 결재 상태: " + approvalLine.getApprState());
	 		        approvalLine.setApprState("C");
	 		        approvalLineRepository.save(approvalLine);
	 		    }

	 		    if (apprOrder == 1) {
	 		        ApprovalLine secondLine = approvalLineRepository.findByApprovalApprNoAndApprOrder(apprNo, 2);
	 		        if (secondLine != null) {
	 		            secondLine.setApprState("S");
	 		            approvalLineRepository.save(secondLine);
	 		        }
	 		    }
	 	    }

	 	
	 	// 결재선 저장 메서드 추가
	 	    public ApprovalLine saveApprovalLine(ApprovalLineDto lineDto) {
	 	    System.out.println("Saving ApprovalLine for Approval No: " + lineDto.getAppr_no());
	 	       System.out.println("ApprovalLine Details: Order - " + lineDto.getAppr_order() + ", Role - " + lineDto.getAppr_role());

	 	       // DTO에서 apprNo를 통해 Approval 엔티티를 조회
	 	       Approval approval = approvalRepository.findById(lineDto.getAppr_no())
	 	              .orElseThrow(() -> new IllegalArgumentException("Invalid approval id: " + lineDto.getAppr_no()));
	 	       // Employee 엔티티 조회
	 	      Employee employee = employeeRepository.findById(lineDto.getEmp_code())  // emp_code로 Employee 조회
	 	              .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + lineDto.getEmp_code()));
	 	      
	 	       // 빌더 패턴을 사용하여 ApprovalLine 생성
	 	       ApprovalLine approvalLine = ApprovalLine.builder()
	 	               .apprOrder(lineDto.getAppr_order()) // 결재 순서 설정
	 	               .apprRole(lineDto.getAppr_role())   // 결재 역할 설정
	 	               .apprState(lineDto.getAppr_state()) // 결재 상태 설정
	 	               .approval(approval) // 결재선에 Approval 엔티티 연결
	 	               .employee(employee) 
	 	               .build();

	 	   
	 	      return approvalLineRepository.save(approvalLine);
	 	    }

		
	}
		
	
	
	
	
	
	
