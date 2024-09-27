package com.cc.approval.domain;

import java.util.List;

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

@Entity
@Table(name="approval_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
	@JoinColumn(name="appr_writer_no")
	private Employee employee;
	
	@Column(name="appr_order")
	private Integer apprOrder;
	
	@Column(name="appr_role")
	private String apprRole;
	
	@Column(name="appr_state")
	private String apprState;
	
	
	
	
	
	
	
	
}
