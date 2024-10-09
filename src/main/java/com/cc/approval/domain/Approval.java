package com.cc.approval.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cc.employee.domain.Employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="approval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Approval {
	
	
	@Id
	@Column(name="appr_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apprNo;
	

	@ManyToOne
	@JoinColumn(name="appr_form_no")
	private ApprForm apprForm;
	
	
	@Column(name="appr_state")
	private String apprState;
	
	@Column(name="appr_title")
	private String apprTitle;
	
	@Column(name="appr_content")
	private String apprContent;
	
	@Column(name="reject_content")
	private String rejectContent;
	
	@Column(name="draft_day")
	@CreationTimestamp
	private LocalDate draftDay;
	
	@Column(name="appr_holi_start")
	private LocalDate apprHoliStart;
	
	@Column(name="appr_holi_end")
	private LocalDate apprHoliEnd;
	
	@Column(name="appr_holi_use_count")
	private Integer apprHoliUseCount;

	@ManyToOne
	@JoinColumn(name="appr_writer_code")
	private Employee employee;
	
	@Column(name="appr_writer_name")
	private String apprWriterName;
	
	@Column(name="is_deleted")
	private String isDeleted;
	
	public void setDeleted() {
	    this.isDeleted = "Y";
	}
	
	@Column(name="docu_no")
	private String docuNo;
	
	// 다대일 관계의 반대쪽을 명시합니다. Approval이 여러 ApprovalLine을 가질 수 있음.
    @OneToMany(mappedBy = "approval", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalLine> approvalLines;
    
    // Approval 클래스에 날짜를 포맷팅하는 메서드 추가
    public String getFormattedDraftDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // draftDay가 null이면 현재 날짜로 설정
        if (draftDay == null) {
            return LocalDate.now().format(formatter);
        }

        // draftDay를 문자열로 변환
        return draftDay.format(formatter);
    }
    
}