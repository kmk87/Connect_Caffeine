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
@Table(name="user_schedule_color")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class UserScheduleColor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_color_no")
	private Long scheduleColorNo;
	
	@Column(name="schedule_type")
	private Long scheduleType;
	
	@ManyToOne
    @JoinColumn(name="emp_code") 
    private Employee employee; 
	
	@ManyToOne
    @JoinColumn(name="color_no") 
    private Color color; 
	
	
}
