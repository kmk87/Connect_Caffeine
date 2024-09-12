package com.cc.approval.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
public class ApprovalDto {
	private Long appr_no;
	private String appr_state;
	private String appr_title;
	private String appr_content;
	private LocalDateTime draft_day;
	private LocalDateTime appr_date;
	private String appr_docu_no; // 추가된 필드
    private String emp_code;
    private Long appr_form_no;
    private Long appr_writer_code;
    private String appr_writer_name;

 // 날짜를 문자열로 변환하는 메서드 추가
    public String getFormattedDraftDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return draft_day != null ? draft_day.format(formatter) : LocalDateTime.now().format(formatter);
    }
    
	
	public Approval toEntity(Employee employee, ApprForm apprForm) {
		return Approval.builder()
				.apprNo(appr_no)
				.apprState(appr_state)
				.apprTitle(appr_title)
				.apprContent(appr_content)
				.draftDay(draft_day)
				.apprDate(appr_date)
				.employee(employee) 
                .apprForm(apprForm)
				.build();
	}
	
	public ApprovalDto toDto(Approval approval) {
		return ApprovalDto.builder()
				.appr_no(approval.getApprNo())
				.appr_state(approval.getApprState())
				.appr_title(approval.getApprTitle())
				.appr_content(approval.getApprContent())
				.draft_day(approval.getDraftDay())
				.appr_date(approval.getApprDate())
				.appr_writer_code(approval.getEmployee() != null ? approval.getEmployee().getEmpCode() : null)  // Employee의 empCode 설정
                .appr_writer_name(approval.getEmployee() != null ? approval.getEmployee().getEmpName() : null)  // Employee의 empName 설정
                .appr_docu_no(approval.getApprForm() != null ? approval.getApprForm().getApprDocuNo() : null)   // ApprForm의 apprDocuNo 설정
				.build(); 
	}
	
	

		
}
