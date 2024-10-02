package com.cc.annualLeave.domain;

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
@Table(name="annual_leave")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class AnnualLeave {

	   @Id
	   
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name="annual_leave_no")
	   private Long annualLeaveNo;
	   
	   @ManyToOne
	   @JoinColumn(name="emp_code")
	   private Employee employees;
	   
	   @Column(name="annual_leave_start")
	   private LocalDateTime annualLeaveStart;
	   
	   @Column(name="annual_leave_end")
	   private LocalDateTime annualLeaveEnd;
	   
	   @Column(name="annual_leave_use_count")
	   private Long annualLeaveUseCount;
	   
	   @Column(name="annual_leave_left_count")
	   private Long annualLeaveLeftCount;
	   
}
