package com.cc.employee.domain;
import java.time.LocalDateTime;

import com.cc.empGroup.domain.EmpGroup;

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
@Table(name="employee")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Employee {
	
	@Id
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_code")
	private Long empCode;
	
	@ManyToOne
	@JoinColumn(name="group_no")
	private EmpGroup empGroup;
	
	@Column(name="emp_job_code")
	private String empJobCode;
	
	@Column(name="emp_job_name")
	private String empJobName;
	
	@Column(name="emp_name")
	private String empName;
	
	@Column(name="emp_account")
	private String empAccount;
	
	@Column(name="emp_pw")
	private String empPw;
	
	@Column(name="emp_postcode")
	private Long empPostcode;
	
	@Column(name="emp_addr")
	private String empAddr;
	
	@Column(name="emp_addr_detail")
	private String empAddrDetail;
	
	@Column(name="emp_reg_no")
	private String empRegNo;
	
	@Column(name="emp_email")
	private String empEmail;
	
	@Column(name="emp_phone")
	private String empPhone;
	
	@Column(name="emp_desk_phone")
	private String empDeskPhone;
	
	@Column(name="emp_hiredate")
	private LocalDateTime empHiredate;
	
	@Column(name="emp_resign")
	private String empResign;
	
	@Column(name="emp_resigndate")
	private LocalDateTime empResigndate;
	
	@Column(name="emp_memo")
	private String empMemo;
	
	@Column(name="emp_holiday")
	private Long empHoliday;
	
	@Column(name="emp_img_file_name") 
	private String empImgFileName;

	@Column(name="emp_img_file_path") 
	private String empImgFilePath;

}


