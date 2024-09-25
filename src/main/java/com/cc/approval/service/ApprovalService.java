package com.cc.approval.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
				.apprState(dto.getAppr_state() != null ? dto.getAppr_state() : "S")
				.isDeleted(dto.getIs_deleted() != null ? dto.getIs_deleted() : "N")
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
	public List<ApprovalDto> getAllApprovals(int size) {
		// 현재 로그인한 사용자의 ID 가져오기
	    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    // 페이지 크기를 동적으로 받아서 처리 (예: 5개 또는 10개)
	    Pageable pageable = PageRequest.of(0, size); 
	    
	    // 레포지토리에서 내림차순으로 정렬된 상위 5개의 데이터 조회
        List<Approval> apprList = approvalRepository.findByEmployeeAccountOrderByDraftDayDesc(currentUserId, pageable).getContent();
        
        // 로그 추가
        System.out.println("Retrieved approvals: " + apprList.size());
        
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
	                .build();
	    } else {
	        // 기존 객체 업데이트
	        temp.setApprTitle(dto.getAppr_title());
	        temp.setApprContent(dto.getAppr_content());
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
	
	
	
}