package com.cc.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.domain.MonthlyLeaveDto;
import com.cc.attendance.service.AnnualLeaveService;
import com.cc.attendance.service.AttendanceService;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;


@Controller
public class AttendanceController {
	
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private final AttendanceService attendanceService;
	private final AnnualLeaveService annualLeaveService;
	
	@Autowired
	public AttendanceController(EmployeeService employeeService, EmpGroupService empGroupService
			, AttendanceService attendanceService, AnnualLeaveService annualLeaveService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
		this.attendanceService = attendanceService;
		this.annualLeaveService = annualLeaveService;
	}
	
	// 출퇴근 정보 기록 -> 홈컨트롤러로 이동?
	@PostMapping("/attendanceRecord")
	@ResponseBody
	public Map<String, String> recordAttendance(@RequestParam("action") String action){
        // 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String empAccount = user.getUsername();
        Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
        
        AttendanceDto attendanceDto = attendanceService.recordAttendance(empCode, action);
//        System.out.println("한번 갔다온, 컨트롤러의 attendanceDto : "+ attendanceDto); 성공
        

        Map<String, String> response = new HashMap<>();
        if ("start".equals(action)) {
            response.put("attnStart", attendanceDto.getAttn_start().toString());
        } else if ("end".equals(action)) {
            response.put("attnEnd", attendanceDto.getAttn_end().toString());
        }
        
        return response;
    }
	
	
	
	
	// 2. 근태 관리
	@GetMapping("toAttendance")
	public String attendancePage(Model model) {
		
		// 사용자 정보(pk) 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user =(User)authentication.getPrincipal();
	    String empAccount = user.getUsername();
	    Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
	    
	    // (1) 출결 현황
	    List<AttendanceDto> attnList = attendanceService.getAttendancesByEmpCode(empCode);
	    
	    model.addAttribute("attnList", attnList);
	    
	    
	    // (2) 연차 현황
	    List<MonthlyLeaveDto> monthlyLeaveUsage = annualLeaveService.getMonthlyLeaveUsage(empCode);
        model.addAttribute("monthlyLeaveUsage", monthlyLeaveUsage);
        
        
		// 로그인, 사용자 정보
		
		EmployeeDto userDto = employeeService.selectEmployeeOne(empCode);
		model.addAttribute("userDto", userDto);
        
		return "employee/attendance";
	}

	
	
	
}
