package com.cc.empGroup.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.cc.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="emp_group")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class EmpGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_no")
	private Long groupNo;
	
	@Column(name="group_parent_no")
	private Long groupParentNo;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="group_leader_code")
	private Long groupLeaderCode;
	
	@Column(name="group_reg_date")
	private LocalDateTime groupRegDate;
	
	@Column(name="group_headcount")
	private Long groupHeadcount;
	
	@Column(name="group_location")
	private String groupLocation;
	
	@Column(name="group_status")
	private String groupStatus;
	
	@Column(name="group_level")
	private String groupLevel;
	
	@Column(name="group_explain")
	private String groupExplain;
	
    @OneToMany(mappedBy="empGroup")
    private List<Employee> employees;
	
}