package com.cc.approval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	private final ApprFormRepository apprFormRepository;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public ApprovalService(ApprovalRepository approvalRepository,ApprFormRepository apprFormRepository,
			EmployeeRepository employeeRepository) {
		
		this.approvalRepository = approvalRepository;
		this.apprFormRepository = apprFormRepository;
		this.employeeRepository = employeeRepository;
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
		System.out.println("apprWriter:"+apprWriter);
		Employee emp = employeeRepository.findByempCode(apprWriter);
		System.out.println("employee:"+emp);
		
		System.out.println("dto다"+dto);
		Approval approval = Approval.builder()
				.apprNo(dto.getAppr_no())
				.apprTitle(dto.getAppr_title())
				.apprContent(dto.getAppr_content())
				.apprWriterName(emp.getEmpName())
				.employee(emp)
				.build();
		System.out.println("서비스 dto:"+dto);
		
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
		// 현재 로그인한 사용자의 ID를 가져옴
	    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
	    
	    // 로그인한 사용자의 상신 리스트만 조회
	    List<Approval> apprList = approvalRepository.findByEmployeeAccount(currentUserId);
	    
	    List<ApprovalDto> apprDtoList = new ArrayList<ApprovalDto>();
	    for (Approval a : apprList) {
	        ApprovalDto approvalDto = new ApprovalDto().toDto(a);
	        apprDtoList.add(approvalDto);
	        System.out.println("서비스 디티오: " + apprDtoList);
	    }
	    return apprDtoList;
    }
	
	
}