package com.cc.approval.domain;

import java.time.LocalDate;

import com.cc.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="temporary_storage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class TemporaryStorage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tem_no")
	private Long temNo;
	
	@ManyToOne
	@JoinColumn(name="emp_code")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="appr_form_no")
	private ApprForm apprForm;
	
	@Column(name="appr_title")
	private String apprTitle;

	@Column(name="appr_content")
	private String apprContent;
	
	@Column(name="appr_holi_use_count")
	private Integer apprHoliUseCount;
	
	@Column(name="appr_holi_start")
	private LocalDate apprHoliStart;
	
	@Column(name="appr_holi_end")
	private LocalDate apprHoliEnd;
	
}
