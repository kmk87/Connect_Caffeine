package com.cc.approval.domain;

import java.time.LocalDateTime;

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
public class ApprovalDto {
	private Long appr_no;
	private Long emp_code;
	private String appr_state;
	private String appr_title;
	private String appr_content;
	private LocalDateTime draft_day;
	private LocalDateTime appr_date;
	
	
	public Approval toEntity() {
		return Approval.builder()
				.apprNo(appr_no)
				.empCode(emp_code)
				.apprState(appr_state)
				.apprTitle(appr_title)
				.apprContent(appr_content)
				.draftDay(draft_day)
				.apprDate(appr_date)
				.build();
	}
	
	public ApprovalDto toDto(Approval approval) {
		return ApprovalDto.builder()
				.appr_no(approval.getApprNo())
				.emp_code(approval.getEmpCode())
				.appr_state(approval.getApprState())
				.appr_title(approval.getApprTitle())
				.appr_content(approval.getApprContent())
				.draft_day(approval.getDraftDay())
				.appr_date(approval.getApprDate())
				.build();
	}
	
		
}
