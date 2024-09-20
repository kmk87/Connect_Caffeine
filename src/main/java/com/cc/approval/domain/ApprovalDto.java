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
    private String formName;
    private String group_name;
    private Integer appr_holi_use_count;
    //private Long apprFormNo;

    // 날짜를 문자열로 변환하는 메서드 추가
    public String getFormattedDraftDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return draft_day != null ? draft_day.format(formatter) : LocalDateTime.now().format(formatter);
    }
    
    // 결재 양식 이름 설정 메소드
    public void setFormName() {
        if (this.appr_form_no != null) {
            switch (this.appr_form_no .intValue()) {
                case 1:
                    this.formName = "휴가신청서";
                    break;
                case 2:
                    this.formName = "사유서";
                    break;
                case 3:
                    this.formName = "품의서";
                    break;
                default:
                    this.formName = "알 수 없음"; 
                    break;
            }
        } else {
            this.formName = "알 수 없음";
        }
    }
    
    // 결재 상태를 문자열로 변환하는 메소드
    public String getApprStateDisplay() {
        switch (this.appr_state) {
            case "s":
                return "결재대기";
            case "r":
                return "반려";
            case "c":
                return "결재완료";
            case "a":
                return "1차승인";
            default:
                return "알 수 없음"; 
        }
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
	
//	public ApprovalDto toDto(Approval approval) {
//		return ApprovalDto.builder()
//				.appr_no(approval.getApprNo())
//				.appr_state(approval.getApprState())
//				.appr_title(approval.getApprTitle())
//				.appr_content(approval.getApprContent())
//				.draft_day(approval.getDraftDay())
//				.appr_date(approval.getApprDate())
//				.appr_writer_code(approval.getEmployee() != null ? approval.getEmployee().getEmpCode() : null)  // Employee의 empCode 설정
//                .appr_writer_name(approval.getEmployee() != null ? approval.getEmployee().getEmpName() : null)  // Employee의 empName 설정
//                .appr_docu_no(approval.getApprForm() != null ? approval.getApprForm().getApprDocuNo() : null)   // ApprForm의 apprDocuNo 설정
//                .appr_form_no(approval.getApprForm().getApprFormNo())
//                .build(); 
//		
//	}
	
	public ApprovalDto toDto(Approval approval) {
	    ApprovalDto dto = ApprovalDto.builder()
	            .appr_no(approval.getApprNo())
	            .appr_state(approval.getApprState())
	            .appr_title(approval.getApprTitle())
	            .appr_content(approval.getApprContent())
	            .draft_day(approval.getDraftDay())
	            .appr_date(approval.getApprDate())
	            .appr_writer_code(approval.getEmployee() != null ? approval.getEmployee().getEmpCode() : null)  // Employee의 empCode 설정
	            .appr_writer_name(approval.getEmployee() != null ? approval.getEmployee().getEmpName() : null)  // Employee의 empName 설정
	            .appr_docu_no(approval.getApprForm() != null ? approval.getApprForm().getApprDocuNo() : null)   // ApprForm의 apprDocuNo 설정
	            .appr_form_no(approval.getApprForm() != null ? approval.getApprForm().getApprFormNo() : null)   // ApprForm의 apprFormNo 설정
	            .build();

	    // 결재양식 이름 설정 메소드 호출
	    dto.setFormName();

	    return dto;
	}

		
}
