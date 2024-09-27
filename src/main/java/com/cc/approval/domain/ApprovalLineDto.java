package com.cc.approval.domain;

import com.cc.employee.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ApprovalLineDto {
	private Long appr_line_id;
	private Long appr_no;
	private Long appr_writer_no;
	private Integer appr_order;
	private String appr_role;
	private String appr_state;
	
	
	
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
				.appr_writer_no(approvalLine.getEmployee() != null ? approvalLine.getEmployee().getEmpCode() : null)
				.appr_role(approvalLine.getApprRole())
				.appr_state(approvalLine.getApprState())
				.build();
		
		return dto;
	}
	
	
	
}
