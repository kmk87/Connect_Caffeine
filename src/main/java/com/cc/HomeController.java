package com.cc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.service.AttendanceService;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.tree.service.OrgService;
@Controller
public class HomeController {

	private final EmployeeService employeeService;
	private final OrgService orgService;
	private final AttendanceService attendanceService;

	@Autowired
	public HomeController(EmployeeService employeeService, OrgService orgService, AttendanceService attendanceService) {

		this.employeeService = employeeService;
		this.orgService = orgService;
		this.attendanceService = attendanceService;
	}

	@GetMapping({ "", "/" })
	public String home(Model model) {
		// 1. 로그인, 사용자 정보
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String empAccount = user.getUsername();
		Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
		
		EmployeeDto userDto = employeeService.selectEmployeeOne(empCode);
		model.addAttribute("userDto", userDto);

		
		// 2. 오늘 날짜 출퇴근
		AttendanceDto attnDto = attendanceService.getTodayAttendance(empCode);
        model.addAttribute("attnDto", attnDto);

		
		return "index";
	}
}
