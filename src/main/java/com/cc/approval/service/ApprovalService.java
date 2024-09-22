package com.cc.approval.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.TemporaryStorage;
import com.cc.approval.domain.TemporaryStorageDto;
import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.approval.repository.TemporaryStorageRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	private final ApprFormRepository apprFormRepository;
	private final EmployeeRepository employeeRepository;
	private final TemporaryStorageRepository temporaryStorageRepository;
	
	@Autowired
	public ApprovalService(ApprovalRepository approvalRepository,ApprFormRepository apprFormRepository,
			EmployeeRepository employeeRepository,TemporaryStorageRepository temporaryStorageRepository) {
		
		this.approvalRepository = approvalRepository;
		this.apprFormRepository = apprFormRepository;
		this.employeeRepository = employeeRepository;
		this.temporaryStorageRepository = temporaryStorageRepository;
	}
	
	public Approval getDraftInfoOne(ApprovalDto dto) {
		
		// 세션
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUserAccount = authentication.getName();
	    
	    Employee employee = employeeRepository.findByempAccount(loggedInUserAccount);
		
	    dto.setAppr_writer_code(employee.getEmpCode());
	    dto.setAppr_writer_name(employee.getEmpName());
	    
		// 기안서 등록
		Long apprWriter = dto.getAppr_writer_code();
		
		Employee emp = employeeRepository.findByempCode(apprWriter);
		System.out.println("appr_form_no: " + dto.getAppr_form_no());
		ApprForm apprFo = apprFormRepository.findByapprFormNo(dto.getAppr_form_no());
		
		
		Approval approval = Approval.builder()
				.apprNo(dto.getAppr_no())
				.apprTitle(dto.getAppr_title())
				.apprContent(dto.getAppr_content())
				.apprWriterName(emp.getEmpName())
				.apprForm(apprFo)
				.employee(emp)
				.apprState(dto.getAppr_state() != null ? dto.getAppr_state() : "s")
				.build();
		
		
		return approvalRepository.save(approval);
		
		
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
	public List<ApprovalDto> getAllApprovals() {
		// 현재 로그인한 사용자의 ID 가져오기
	    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    
	    // 레포지토리에서 내림차순으로 정렬된 상위 5개의 데이터 조회
        List<Approval> apprList = approvalRepository.findTop5ByEmployeeAccountOrderByDraftDayDesc(currentUserId);

        // Approval 엔티티를 ApprovalDto로 변환
        return apprList.stream()
                       .map(approval -> new ApprovalDto().toDto(approval))
                       .collect(Collectors.toList());
    }
	
	
	// 기안서 상세 조회
	public ApprovalDto selectapprovalOne(Long appr_no) {
		Approval approval = approvalRepository.findByApprNo(appr_no);
		return new ApprovalDto().toDto(approval);
	}
	
	
	// 기안서 삭제 -> 비활성화
//	public void disableApproval(Long id) {
//		Optional<Approval> approvalOptional = approvalRepository.findById(id);
//	    
//	    if (approvalOptional.isPresent()) {
//	        Approval approval = approvalOptional.get();
//	        approval.setDeleted();
//	        approvalRepository.save(approval);
//	    } else {
//	        // 예외 없이 처리하는 방법: 로그 남기기, 메시지 출력 등
//	        System.out.println("Approval not found with id: " + id);
//	        // 로그 남기기
//	        // logger.warn("Approval not found with id: {}", id);
//	    }
//	}
//	
//	
	// 기안서 임시저장
	public boolean updateApprWithEmpCode(TemporaryStorageDto dto) {
	    // 로그인된 사용자의 memId(empAccount)를 통해 empCode 가져오기
	    String memId = SecurityContextHolder.getContext().getAuthentication().getName(); // 로그인된 사용자 ID
	    System.out.println("서비스memId: "+memId);
	    
	    Employee employee = employeeRepository.findByempAccount(memId);
	    System.out.println("서비스employee: "+employee);
	    
	    if (employee == null) {
	        return false;  // 사원 정보가 없으면 false 반환
	    }
	    
	    dto.setEmp_code(employee.getEmpCode());  // DTO에 empCode 설정

	    return updateAppr(dto) != null;  // 임시 저장 수행
	}
	
	
	
	public TemporaryStorage updateAppr(TemporaryStorageDto dto) {
		TemporaryStorageDto temp = selectTemporaryStorageOne(dto.getAppr_no());
		
		temp.setAppr_title(dto.getAppr_title());
		temp.setAppr_content(dto.getAppr_content());
		
		// Approval, Employee, ApprForm 객체를 넘겨주어야 함
	    Approval approval = approvalRepository.findByApprNo(dto.getAppr_no());
	    Employee employee = employeeRepository.findByempCode(dto.getEmp_code());
	    ApprForm apprForm = apprFormRepository.findByapprFormNo(dto.getAppr_form_no());
	    
	    System.out.println("emp code: "+employee);
	    
	    TemporaryStorage temporaryStorage = temp.toEntity(approval, employee, apprForm);  // 필요한 객체들 전달
	
		
		return temporaryStorageRepository.save(temporaryStorage);
	}


	public TemporaryStorageDto selectTemporaryStorageOne(Long appr_no) {
	    TemporaryStorage temp = temporaryStorageRepository.findByApprovalApprNo(appr_no); // appr_no로 조회
	    return TemporaryStorageDto.builder()
	            .tem_no(temp.getTemNo())
	            .appr_no(temp.getApproval().getApprNo())
	            .appr_title(temp.getApproval().getApprTitle())
	            .appr_content(temp.getApproval().getApprContent())
	            .build();
	}
	
	
	
	
	
	
	
}