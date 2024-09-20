package com.cc.approval.domain;

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
public class ApprFormDto {
	private Long appr_form_no;
	private String appr_form_type;
	private String appr_docu_no;
	
	
	public ApprForm toEntity() {
		return ApprForm.builder()
				.apprFormNo(appr_form_no)
				.apprFormType(appr_form_type)
				.apprDocuNo(appr_docu_no)
				.build();
	}
	
	
	public ApprFormDto toDto(ApprForm apprForm) {
		return ApprFormDto.builder()
				.appr_form_no(apprForm.getApprFormNo())
				.appr_form_type(apprForm.getApprFormType())
				.appr_docu_no(apprForm.getApprDocuNo())
				.build();
	}
	
	
	
}
