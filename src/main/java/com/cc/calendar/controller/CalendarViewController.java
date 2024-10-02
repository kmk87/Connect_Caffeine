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
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;


@Controller
public class CalendarViewController {
	
	private final CalendarService calendarService;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	private final ColorService colorService;
	private final UserScheduleColorService userScheduleColorService;
	private final EmpGroupService empGroupService;
	
	@Autowired
	public CalendarViewController(CalendarService calendarService, EmployeeService employeeService, 
		EmployeeRepository employeeRepository,ColorService colorService,UserScheduleColorService userScheduleColorService,
		EmpGroupService empGroupService) {
		this.calendarService = calendarService;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
		this.colorService = colorService;
		this.userScheduleColorService = userScheduleColorService;
		this.empGroupService = empGroupService;
	}
	
	  @GetMapping("/calendar")
	    public String calendarView(Model model) {

		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  User user =(User)authentication.getPrincipal();
		  String empAccount = user.getUsername();
		  System.out.println("empAccount : "+empAccount);
		  Long empCode = employeeService.findEmpCodeByEmpName(empAccount);  
		  System.out.println("empCode : "+empCode);
		  Long teamNo = ((EmployeeService) employeeService).getGroupNoByEmpCode(empCode);
		  System.out.println("teamNo : "+teamNo);
		  Long deptNo = empGroupService.getGroupNoByEmpCode(teamNo);
		  System.out.println("deptNo : "+deptNo);
		 
		  

		  
		  
		  // 일정 데이터 가져오기
	        List<CalendarDto> resultList = calendarService.selectCalendarList(empCode, deptNo, teamNo);
	        model.addAttribute("resultList", resultList);
	        // 데이터가 제대로 전달되고 있는지 확인
	        System.out.println("일정 리스트: " + resultList);
	        
	        
	        // 색상 데이터 가져오기(팔레트)
	        List<String> colors = colorService.getAllColors(); // ColorService를 통해 색상 목록을 가져옴
	        model.addAttribute("colors", colors); // 색상 목록을 모델에 추가
	        
	        
	        
	       
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
