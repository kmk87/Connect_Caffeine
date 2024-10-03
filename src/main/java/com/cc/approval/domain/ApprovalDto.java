package com.cc.approval.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	private LocalDate draft_day;
	private LocalDate appr_date;
    private String emp_code;
    private Long appr_form_no;
    private Long appr_writer_code;
    private String appr_writer_name;
    private String formName;
    private String group_name;
    private Integer appr_holi_use_count;
    private String is_deleted;
    private LocalDate appr_holi_start; 
    private LocalDate appr_holi_end;
    private String docu_no;
    private String emp_account;
    private Long tem_no;
    
    private String signImagePath1;	// 1차 결재자 서명 경로
    private String signImagePath2;  // 2차 결재자 서명 경로
    
    
    // 결재선 리스트 추가
    private List<ApprovalLineDto> approvalLineList;
    
    
    // 서명 경로 설정 메소드 (이미지 경로가 null이면 기본 이미지 경로 설정)
    public String getSignImagePath1() {
        return signImagePath1 != null ? signImagePath1 : "/upload/default_signature.png"; // 기본 이미지 설정 가능
    }

    public String getSignImagePath2() {
        return signImagePath2 != null ? signImagePath2 : "/upload/default_signature.png"; // 기본 이미지 설정 가능
    }
    
    // 날짜를 문자열로 변환하는 메소드 추가
    public String getFormattedDraftDay() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // draft_day가 null이면 현재 날짜로 설정
        if (draft_day == null) {
            draft_day = LocalDate.now();
        }

        // draft_day를 문자열로 변환
        return draft_day.format(formatter);
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
            case "S":
                return "결재대기";
            case "R":
                return "반려";
            case "C":
                return "결재완료";
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
				.apprHoliStart(appr_holi_start) 
	            .apprHoliEnd(appr_holi_end)     
	            .apprHoliUseCount(appr_holi_use_count)
				.isDeleted(is_deleted)
				.docuNo(docu_no)
				.employee(employee) 
                .apprForm(apprForm)
				.build();
	}
	
	
	public ApprovalDto toDto(Approval approval) {
	    ApprovalDto dto = ApprovalDto.builder()
	            .appr_no(approval.getApprNo())
	            .appr_state(approval.getApprState() != null ? approval.getApprState() : "S")
	            .appr_title(approval.getApprTitle())
	            .appr_content(approval.getApprContent())
	            .draft_day(approval.getDraftDay())
	            .appr_date(approval.getApprDate())
	            .is_deleted(approval.getIsDeleted())
	            .docu_no(approval.getDocuNo())
	            .appr_holi_start(approval.getApprHoliStart())
	            .appr_holi_end(approval.getApprHoliEnd())
	            .appr_holi_use_count(approval.getApprHoliUseCount())
	            .appr_writer_code(approval.getEmployee() != null ? approval.getEmployee().getEmpCode() : null)  // Employee의 empCode 설정
	            .appr_writer_name(approval.getEmployee() != null ? approval.getEmployee().getEmpName() : null)  // Employee의 empName 설정
	            .appr_form_no(approval.getApprForm() != null ? approval.getApprForm().getApprFormNo() : null)   // ApprForm의 apprFormNo 설정
	            .build();

	    // 결재양식 이름 설정 메소드 호출
	    dto.setFormName();
	    
	    // 결재라인에서 서명 이미지 경로를 가져오는 로직 추가
	    List<ApprovalLine> approvalLines = approval.getApprovalLines();
	    if (approvalLines != null && !approvalLines.isEmpty()) {
	        // 1차 결재자의 서명 경로 추가
	        dto.setSignImagePath1(approvalLines.get(0).getEmployee().getEmpSignatureImagePath());

	        // 2차 결재자가 있는 경우 서명 경로 추가
	        if (approvalLines.size() > 1) {
	            dto.setSignImagePath2(approvalLines.get(1).getEmployee().getEmpSignatureImagePath());
	        }
	    }
	    

	    return dto;
	}

		
}
