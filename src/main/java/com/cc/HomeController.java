package com.cc;

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

import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.service.AttendanceService;
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.tree.service.OrgService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		
		EmployeeDto dto = employeeService.selectEmployeeOne(empCode);
		System.out.println("컨트롤러의 dto: " + dto);
		model.addAttribute("dto", dto);

		
		// 2. 오늘 날짜 출퇴근
		List<AttendanceDto> attnList = attendanceService.getAttendancesByEmpCode(empCode);
        model.addAttribute("attnList", attnList);
        
		
		
		// <조직도>
		// 3. 팀 정보
//		List<Map<String, Object>> teamResult = orgService.getOrgTeamTree();
//
//		// 앞에 전사, 사장, 부사장 정보 추가
//		Map<String, Object> teamNode1 = new HashMap<>(); // 전사
//
//		teamNode1.put("id", 1);
//		teamNode1.put("parent", "#");
//		teamNode1.put("text", "전사");
//		teamNode1.put("primaryKey", "없음.");
//		teamNode1.put("type", "master");

		/*
		 * // 두번째 앞에 추가할 새로운 객체 생성 Map<String, Object> teamNode2 = new HashMap<>(); //
		 * 사장
		 * 
		 * teamNode2.put("id", 2); teamNode2.put("parent", String.valueOf(1));
		 * teamNode2.put("text", "마동석"); teamNode2.put("primaryKey", "사장");
		 * 
		 * // 세번째 앞에 추가할 새로운 객체 생성 Map<String, Object> teamNode3 = new HashMap<>(); //
		 * 부사장
		 * 
		 * teamNode3.put("id", 3); teamNode3.put("parent", String.valueOf(1));
		 * teamNode3.put("text", "박보영"); teamNode3.put("primaryKey", "부사장");
		 * 
		 * 
		 *  teamResult.add(1, teamNode2); teamResult.add(2,
		 * teamNode3);
		 */
//		teamResult.add(0, teamNode1);
//		
//		String teamObj = null;
//
//		try {
//			teamObj = new ObjectMapper().writeValueAsString(teamResult);
//
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		// 4. 사원 정보
//		List<Map<String, Object>> empResult = orgService.getOrgEmpTree();
//
//		String empObj = null;
//
//		try {
//			empObj = new ObjectMapper().writeValueAsString(empResult);
//
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		model.addAttribute("teamObj", teamObj);
//		model.addAttribute("empObj", empObj);
		
		
		return "index";
	}
}
