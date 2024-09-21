package com.cc.calendar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.service.CalendarService;
import com.cc.calendar.service.ColorService;
import com.cc.calendar.service.UserScheduleColorService;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;


@Controller
public class CalendarViewController {
	
	private final CalendarService calendarService;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	private final ColorService colorService;
	private final UserScheduleColorService userScheduleColorService;
	
	@Autowired
	public CalendarViewController(CalendarService calendarService, EmployeeService employeeService, 
			EmployeeRepository employeeRepository,ColorService colorService,UserScheduleColorService userScheduleColorService) {
		this.calendarService = calendarService;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
		this.colorService = colorService;
		this.userScheduleColorService = userScheduleColorService;
	}
	
	  @GetMapping("/calendar")
	    public String calendarView(Model model) {
	        // 일정 데이터 가져오기
	        List<CalendarDto> resultList = calendarService.selectCalendarList();
	        model.addAttribute("resultList", resultList);
	        
	        // 색상 데이터 가져오기(팔레트)
	        List<String> colors = colorService.getAllColors(); // ColorService를 통해 색상 목록을 가져옴
	        model.addAttribute("colors", colors); // 색상 목록을 모델에 추가
	        
	        
	        
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        User user =(User)authentication.getPrincipal();
	        String empAccount = user.getUsername();
	        System.out.println("empAccount : "+empAccount);
	        Long empCode = employeeService.findEmpCodeByEmpName(empAccount);  
	        System.out.println("empCode : "+empCode);
	       
	        // 일정 타입별로 색상을 가져오기
	        Map<Long, String> userColors = new HashMap<>();
	        for (long scheduleType = 1; scheduleType <= 4; scheduleType++) {
	            String colorCode = userScheduleColorService.getColorForSpecificScheduleType(empCode, scheduleType);
	            System.out.println("Color for scheduleType " + scheduleType + ": " + colorCode);
	            userColors.put(scheduleType, colorCode);
	        }
	        
	        // 모델에 userColors 전달
	        model.addAttribute("userColors", userColors);
	        System.out.println("userColors : "+userColors);
	        
	        return "/calendar/schedule"; // 일정 페이지로 이동
	    }

	  
	
	  
	@GetMapping("calendar/{schedule_no}")
	public ResponseEntity<CalendarDto> getScheduleDetails(@PathVariable("schedule_no") Long scheduleNo) {
	    CalendarDto schedule = calendarService.selectScheduleOne(scheduleNo);
	    return ResponseEntity.ok(schedule);
	}


	
}
