package com.cc.calendar.domain;

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
import lombok.Setter;

@Entity
@Table(name="schedule")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Calendar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_no")
	private Long scheduleNo;
	
	@Column(name="schedule_title")
	private String scheduleTitle;
	
	@Column(name="schedule_content")
	private String scheduleContent;
	
	@Column(name="schedule_type")
	private int scheduleType;
	
	@Column(name="location")
	private String location;
	
	@Column(name="start_time")
	private LocalDateTime startTime;
	
	@Column(name="end_time")
	private LocalDateTime endTime;
	
	@ManyToOne
    @JoinColumn(name="emp_code") 
    private Employee employee;  
	
}
