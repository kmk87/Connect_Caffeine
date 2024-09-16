package com.cc.calendar.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.service.CalendarService;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;


@Controller
public class CalendarViewController {
	
	private final CalendarService calendarService;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public CalendarViewController(CalendarService calendarService, EmployeeService employeeService, EmployeeRepository employeeRepository) {
		this.calendarService = calendarService;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
	}
	
	// 일정 목록 뷰 로드
    @GetMapping("/calendar")
    public String calendarView(Model model) {
        List<CalendarDto> resultList = calendarService.selectCalendarList();
        model.addAttribute("resultList", resultList);
        return "/calendar/schedule";
    }

	
	@GetMapping("calendar/{schedule_no}")
	public ResponseEntity<CalendarDto> getScheduleDetails(@PathVariable("schedule_no") Long scheduleNo) {
	    CalendarDto schedule = calendarService.selectScheduleOne(scheduleNo);
	    return ResponseEntity.ok(schedule);
	}


	
}
