package com.cc.approval.domain;

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
	private String appr_role;
	private String appr_state;
	private String appr_writer_name;
	
	// ApprWriterName의 getter 메서드
    public String getApprWriterName() {
        return appr_writer_name;
    }
	
	public ApprovalLine toEntity(Employee employee, Approval approval) {
		
		return ApprovalLine.builder()
				.apprLineId(appr_line_id)
				.apprOrder(appr_order)
				.apprRole(appr_role)
				.apprState(appr_state)
				.employee(employee)
				.approval(approval)
				.build();
	}
	
	
	
	public ApprovalLineDto toDto(ApprovalLine approvalLine) {
		ApprovalLineDto dto = ApprovalLineDto.builder()
				.appr_line_id(approvalLine.getApprLineId())
				.appr_no(approvalLine.getApproval() != null ? approvalLine.getApproval().getApprNo() : null)
				.emp_code(approvalLine.getEmployee() != null ? approvalLine.getEmployee().getEmpCode() : null)  // Employee에서 empCode 가져옴
				.appr_order(approvalLine.getApprOrder())
				.appr_role(approvalLine.getApprRole())
				.appr_state(approvalLine.getApprState())
				.appr_writer_name(approvalLine.getEmployee() != null ? approvalLine.getEmployee().getEmpName() : null)
				.build();
		
		return dto;
	}
	

}