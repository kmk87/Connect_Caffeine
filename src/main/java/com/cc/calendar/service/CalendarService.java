package com.cc.calendar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.calendar.domain.Calendar;
import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.repository.CalendarRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class CalendarService {
	
	private final EmployeeRepository employeeRepository;
	private final CalendarRepository calendarRepository;
	
	@Autowired
	public CalendarService(EmployeeRepository employeeRepository, CalendarRepository calendarRepository) {
		this.employeeRepository = employeeRepository;
		this.calendarRepository = calendarRepository;
	}
	
	public Calendar createSchedule(CalendarDto dto){
		Long calendarWriter = dto.getCalendar_writer_no();
		Employee employee = employeeRepository.findByempCode(calendarWriter);
		
		
		if (employee == null) {
			throw new IllegalArgumentException("유효하지 않은 emp_code: " + calendarWriter);
		}
		
		Calendar calendar = Calendar.builder()
							.scheduleType(dto.getSchedule_type())
							.scheduleTitle(dto.getSchedule_title())
							.scheduleConent(dto.getSchedule_content())
							.location(dto.getLocation())
							.startTime(dto.getStart_time())
							.endTime(dto.getEnd_time())
							.employee(employee)
							.build();
		return calendarRepository.save(calendar);
		
		
	}
}
