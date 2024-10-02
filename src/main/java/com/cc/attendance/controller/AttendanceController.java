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
import com.cc.attendance.service.AttendanceService;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.service.EmployeeService;


@Controller
public class AttendanceController {
	
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private final AttendanceService attendanceService;
	
	@Autowired
	public AttendanceController(EmployeeService employeeService, EmpGroupService empGroupService
			, AttendanceService attendanceService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
		this.attendanceService = attendanceService;
	}
	
	// 출퇴근 정보 기록
	@PostMapping("/attendanceRecord")
	@ResponseBody
	public Map<String, String> recordAttendance(@RequestParam("action") String action){
        // 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String empAccount = user.getUsername();
        Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
        
        AttendanceDto attendanceDto = attendanceService.recordAttendance(empCode, action);
   
        
        Map<String, String> response = new HashMap<>();
        if ("start".equals(action)) {
            response.put("attnStart", attendanceDto.getAttn_start().toString());
        } else if ("end".equals(action)) {
            response.put("attnEnd", attendanceDto.getAttn_end().toString());
        }
        System.out.println("response가 무엇이니? : "+response);
        
        return response;
    }
	
	
	
	
	// 2. 근태 관리 페이지
	@GetMapping("/toAttendance")
	public String attendancePage(Model model) {
		
		// 사용자 정보(pk) 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user =(User)authentication.getPrincipal();
	    String empAccount = user.getUsername();
	    Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
	    
	    // 출퇴근 기록 가져오기
	    List<AttendanceDto> attnList = attendanceService.getAttendancesByEmpCode(empCode);
	    System.out.println("컨트롤러의 출퇴근 기록: " + attnList);
	    
	    model.addAttribute("attnList", attnList);
	    
		return "employee/attendance";
	}

}
