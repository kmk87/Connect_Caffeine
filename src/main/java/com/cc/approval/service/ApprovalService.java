package com.cc.approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class ApprovalService {
	
	private final ApprovalRepository approvalRepository;
	private final ApprFormRepository apprFormRepository;
	private final EmployeeRepository employeeRepository;

//	@Autowired
//    private ApprovalRepository approvalRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private ApprFormRepository apprFormRepository;
//	
	
	@Autowired
	public ApprovalService(ApprovalRepository approvalRepository,ApprFormRepository apprFormRepository,
			EmployeeRepository employeeRepository) {
		
		this.approvalRepository = approvalRepository;
		this.apprFormRepository = apprFormRepository;
		this.employeeRepository = employeeRepository;
	}
	
	// 기안서에 기존의 정보 가져오기
//	public ApprovalDto getDraftInfoOne(Long apprNo,Long apprFormNo) {
//		
//		Approval approval = approvalRepository.findByapprNo(apprNo);
//		ApprForm apprForm = apprFormRepository.findByapprFormNo(apprFormNo);
//		
//		
//	
//		ApprovalDto approvalDto = ApprovalDto.builder()
//				.appr_no(approval.getApprNo())
//				.appr_form_no(apprForm.getApprFormNo())
//				.appr_state(approval.getApprState())
//				.appr_title(approval.getApprTitle())
//				.appr_content(approval.getApprContent())
//				.draft_day(approval.getDraftDay())
//				.appr_date(approval.getApprDate())
//				.appr_writer_code(approval.getApprWriterCode())
//				.appr_writer_name(approval.getApprWriterName())
//				.build();
//	
//		
////		return ApprovalDto.builder()
////				.appr_no(approvalDto.getAppr_no())
////                .appr_state(approvalDto.getAppr_state())
////                .appr_title(approvalDto.getAppr_title())
////                .appr_content(approvalDto.getAppr_content())
////                .draft_day(approvalDto.getDraft_day())
////                .appr_date(approvalDto.getAppr_date())
////                .appr_docu_no(approvalDto.getAppr_docu_no())
////                .appr_writer_code(approvalDto.getAppr_writer_code())
////                .appr_writer_name(approvalDto.getAppr_writer_name())
////                .build();
//		
//		return approvalDto;
//		
//	}
	
	
	public Approval getDraftInfoOne(ApprovalDto dto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUserAccount = authentication.getName();
	    
	    Employee employee = employeeRepository.findByempAccount(loggedInUserAccount);
		
	    dto.setAppr_writer_code(employee.getEmpCode());
	    dto.setAppr_writer_name(employee.getEmpName());
	    
		// 작성 코드
		Long apprWriter = dto.getAppr_writer_code();
		System.out.println("apprWriter:"+apprWriter);
		Employee emp = employeeRepository.findByempCode(apprWriter);
		System.out.println("employee:"+employee);
		
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
	
//	@Transactional
//	public void saveApproval(ApprovalDto approvalDto) {
//	    // emp_account로 Employee 엔티티 조회
//	    Employee employee = employeeRepository.findByempCode(approvalDto.getEmp_code());
//	    ApprForm apprForm = apprFormRepository.findById(approvalDto.getAppr_form_no().getApprFormNo()).orElse(null);
//	    
//
//
//	    Approval approval = approvalDto.toEntity(employee, apprForm);
//        approvalRepository.save(approval);
//	}
	
	
	
	
	
}
