package com.cc.approval.domain;

import java.io.File;

import com.cc.employee.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApprovalLineDto {
	private Long appr_line_id;
	private Long appr_no;
//	private Long appr_writer_code;
	private Long emp_code;
	private Integer appr_order;
	private int appr_role;
	private String appr_state;
	private String appr_writer_name;
	private String signaturePath;
	
	private String signImagePath1;
    private String signImagePath2;
	
	// ApprWriterName의 getter 메서드
    public String getApprWriterName() {
        return appr_writer_name;
    }
    
    public String getSignaturePath() {
        return signaturePath != null ? signaturePath : "";
    }
    
    
    // ApprovalLine을 이용한 생성자
    public ApprovalLineDto(ApprovalLine approvalLine) {
        this.appr_line_id = approvalLine.getApprLineId();
        this.emp_code = approvalLine.getEmployee().getEmpCode();
        this.appr_writer_name = approvalLine.getEmployee().getEmpName();
        this.appr_order = approvalLine.getApprOrder();
        this.appr_state = approvalLine.getApprState();
    }
	
	public ApprovalLine toEntity(Employee employee, Approval approval) {
		
		return ApprovalLine.builder()
				.apprLineId(appr_line_id)
				.apprOrder(appr_order)
				.apprRole(appr_role)
				.apprState(appr_state)
				.employee(employee)
				.approval(approval)
//				.signaturePath(signature_path)
				.build();
	}
	
	
	
	public ApprovalLineDto toDto(ApprovalLine approvalLine) {
	    ApprovalLineDto dto = ApprovalLineDto.builder()
	        .appr_line_id(approvalLine.getApprLineId())
	        .appr_no(approvalLine.getApproval() != null ? approvalLine.getApproval().getApprNo() : null)
	        .emp_code(approvalLine.getEmployee() != null ? approvalLine.getEmployee().getEmpCode() : null)
	        .appr_order(approvalLine.getApprOrder())
	        .appr_role(approvalLine.getApprRole())
	        .appr_state(approvalLine.getApprState())
	        .appr_writer_name(approvalLine.getEmployee() != null ? approvalLine.getEmployee().getEmpName() : null)
	        .signaturePath(approvalLine.getSignaturePath() != null ? approvalLine.getSignaturePath() : "")  // 서명 경로 처리
	        .signImagePath1(approvalLine.getApprOrder() == 1 ? getFileName(approvalLine.getSignaturePath()) : null)
	        .signImagePath2(approvalLine.getApprOrder() == 2 ? getFileName(approvalLine.getSignaturePath()) : null)
	        .build();

	    return dto;
	}


	


	private String getFileName(String fullPath) {
	    if (fullPath == null || fullPath.isEmpty()) {
	        return null;
	    }
	    // OS에 상관없이 파일 구분자를 제대로 처리하도록 수정
	    return fullPath.substring(fullPath.lastIndexOf("/") + 1); // 슬래시(`/`)로 처리
	}


}