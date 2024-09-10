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
//	private Long color_no;
	private String schedule_title;
	private String schedule_content;
	private int schedule_type;
	private String location;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	
	public Calendar toEntity(Employee employee) {
		return Calendar.builder()
				.scheduleNo(schedule_no)
//				.colorNo(color_no)
				.scheduleTitle(schedule_title)
				.scheduleConent(schedule_content)
				.scheduleType(schedule_type)
				.location(location)
				.startTime(start_time)
				.endTime(end_time)
				.employee(employee)
				.build();
	}
	
	public CalendarDto toDto(Calendar calendar){
		return CalendarDto.builder()
				.schedule_no(calendar.getScheduleNo())
//				.color_no(calendar.getColorNo())
				.schedule_title(calendar.getScheduleTitle())
				.schedule_content(calendar.getScheduleConent())
				.schedule_type(calendar.getScheduleType())
				.location(calendar.getLocation())
				.start_time(calendar.getStartTime())
				.end_time(calendar.getEndTime())
				.build();
	}
			
}
