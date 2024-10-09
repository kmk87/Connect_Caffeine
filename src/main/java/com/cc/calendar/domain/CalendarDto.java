package com.cc.calendar.domain;
import java.time.LocalDateTime;

import com.cc.employee.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CalendarDto {
	

	private Long schedule_no;
	private Long calendar_writer_no;
	private String color_code;
	private String schedule_title;
	private String schedule_content;
	private int schedule_type;
	private String location;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private Long dept_no;
	private Long team_no;
	
	
	public Calendar toEntity(Employee employee) {
		return Calendar.builder()
				.scheduleNo(schedule_no)
				.scheduleTitle(schedule_title)
				.scheduleContent(schedule_content)
				.scheduleType(schedule_type)
				.location(location)
				.startTime(start_time)
				.endTime(end_time)
				.employee(employee) // Employee 객체 설정
				.build();
	}
	
	public CalendarDto toDto(Calendar calendar){
		return CalendarDto.builder()
				.schedule_no(calendar.getScheduleNo())
				.schedule_title(calendar.getScheduleTitle())
				.schedule_content(calendar.getScheduleContent())
				.schedule_type(calendar.getScheduleType())
				.location(calendar.getLocation())
				.start_time(calendar.getStartTime())
				.end_time(calendar.getEndTime())
				.dept_no(calendar.getEmployee().getEmpGroup().getGroupParentNo())
				.team_no(calendar.getEmployee().getEmpGroup().getGroupNo())
				.build();
	}
			
}
