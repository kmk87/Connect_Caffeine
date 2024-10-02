package com.cc.approval.domain;

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
@Table(name="approval_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class ApprovalLine {
	
	@Id
	@Column(name="appr_line_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long apprLineId;
	
	@ManyToOne
    @JoinColumn(name = "appr_no")  
    private Approval approval;
	
	@ManyToOne
	@JoinColumn(name="emp_code")
	private Employee employee;

	@Column(name="appr_order")
	private Integer apprOrder;
	
	@Column(name="appr_role")
	private int apprRole;
	
	@Column(name="appr_state")
	private String apprState;
	

}
