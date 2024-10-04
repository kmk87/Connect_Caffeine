package com.cc.attendance.domain;

import java.time.LocalDateTime;

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

@Entity
@Table(name="attendance")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="attn_no")
	private Long attnNo;
	
	@ManyToOne
	@JoinColumn(name="emp_code")
	private Employee employees;
	 
	@Column(name="attn_start")
	private LocalDateTime attnStart;
	 
	@Column(name="attn_end")
	private LocalDateTime attnEnd;
	 
	@Column(name="worktime")
	private Long worktime;
	 
	@Column(name="attn_status")
	private String attnStatus;
	 
}