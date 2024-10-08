package com.cc.approval.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    
    
    // Approval 엔티티를 이용한 생성자
    public ApprovalDto(Approval approval) {
        this.appr_no = approval.getApprNo();
        this.appr_title = approval.getApprTitle();
        this.appr_content = approval.getApprContent();  // 결재 내용 추가
        this.draft_day = approval.getDraftDay();  // 기안일 추가
        this.appr_holi_start = approval.getApprHoliStart();  // 휴가 시작일 추가
        this.appr_holi_end = approval.getApprHoliEnd();  // 휴가 종료일 추가
        this.appr_holi_use_count = approval.getApprHoliUseCount();  // 휴가 사용 일수 추가
        this.is_deleted = approval.getIsDeleted();  // 삭제 여부 추가
        this.docu_no = approval.getDocuNo();  // 문서 번호 추가
        this.appr_state = approval.getApprState();

        // Employee 정보로부터 작성자의 emp_code와 이름을 설정
        if (approval.getEmployee() != null) {
            this.appr_writer_code = approval.getEmployee().getEmpCode();
            this.appr_writer_name = approval.getEmployee().getEmpName();
        }

        // ApprForm 정보로부터 결재 양식 번호 설정
        if (approval.getApprForm() != null) {
            this.appr_form_no = approval.getApprForm().getApprFormNo();
        }

        // 결재 양식 이름 설정
        this.setFormName();

        // 결재라인에서 서명 이미지 경로 설정
        List<ApprovalLine> approvalLines = approval.getApprovalLines();
        if (approvalLines != null && !approvalLines.isEmpty()) {
            if (approvalLines.get(0).getEmployee() != null) {
                this.signImagePath1 = approvalLines.get(0).getEmployee().getEmpSignatureImagePath();
            }
            if (approvalLines.size() > 1 && approvalLines.get(1).getEmployee() != null) {
                this.signImagePath2 = approvalLines.get(1).getEmployee().getEmpSignatureImagePath();
            }
        }

        // 결재선 리스트 설정 (ApprovalLine -> ApprovalLineDto 변환 필요)
        this.approvalLineList = approvalLines.stream()
            .map(line -> new ApprovalLineDto(line))
            .collect(Collectors.toList());
    }

	
    public Approval toEntity(Employee employee, ApprForm apprForm) {
        return Approval.builder()
                .apprNo(appr_no)
                .apprState(appr_state)
                .apprTitle(appr_title)
                .apprContent(appr_content)
                .draftDay(draft_day != null ? draft_day : LocalDate.now())  // draft_day가 null일 경우 현재 날짜로 설정
                .apprHoliStart(appr_holi_start) 
                .apprHoliEnd(appr_holi_end)     
                .apprHoliUseCount(appr_holi_use_count)
                .isDeleted(is_deleted)
                .docuNo(docu_no)
                .employee(employee)  // 외부에서 전달된 Employee
                .apprForm(apprForm)   // 외부에서 전달된 ApprForm
                .build();
    }
	
	
	public ApprovalDto toDto(Approval approval) {
		ApprovalDto dto = ApprovalDto.builder()
	            .appr_no(approval.getApprNo())
	            .appr_state(approval.getApprState() != null ? approval.getApprState() : "S")  // 기본 상태 'S'
	            .appr_title(approval.getApprTitle())
	            .appr_content(approval.getApprContent())
	            .draft_day(approval.getDraftDay())
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
	        if (approvalLines.get(0).getEmployee() != null) {
	            dto.setSignImagePath1(approvalLines.get(0).getEmployee().getEmpSignatureImagePath());
	        }

	        // 2차 결재자가 있는 경우 서명 경로 추가
	        if (approvalLines.size() > 1 && approvalLines.get(1).getEmployee() != null) {
	            dto.setSignImagePath2(approvalLines.get(1).getEmployee().getEmpSignatureImagePath());
	        }
	    }

	    return dto;
	}

		
}