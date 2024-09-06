package com.cc.approval.domain;

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

@Entity
@Table(name="appr_Form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ApprForm {
	
	@Id
	@Column(name="appr_form_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apprFormNo;
	
	@ManyToOne
	@JoinColumn(name="appr_no")
	private Approval approval;
	

	@Column(name="appr_form_type")
	private String apprFormType;
	
	@Column(name="appr_docu_no")
	private String apprDocuNo;
	
	
}
