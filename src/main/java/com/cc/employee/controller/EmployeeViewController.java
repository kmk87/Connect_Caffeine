package com.cc.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.service.AttendanceService;
import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.job.service.JobService;
import com.cc.tree.service.OrgService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeViewController {
	
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private final JobService jobService;
	private final AttendanceService attendanceService;
	private final OrgService orgService;
	
	@Autowired
	public EmployeeViewController(EmployeeService employeeService, EmpGroupService empGroupService, 
			JobService jobService, AttendanceService attendanceService, OrgService orgService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
		this.jobService = jobService;
		this.attendanceService = attendanceService;
		this.orgService = orgService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "employee/pages-login";
	}
	
	// 1. 사원 등록
	@GetMapping("/employee")
	public String createEmployeePage(Model model) {
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		List<com.cc.job.domain.JobDto> jobList = jobService.selectJobList();
		String inputAccount = employeeService.getInputAccount();
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("jobList", jobList);
		model.addAttribute("inputAccount", inputAccount);
		
		return "employee/create";
	}
	
	
	
	// 2-1. 목록(list)
	@GetMapping("employeeList")
	public String selectEmployeeList(Model model) {
		
		List<EmployeeDto> empDtoList = employeeService.selectEmployeeList();
		
		model.addAttribute("empDtoList", empDtoList);
		
		// 2. 조직도
		// (1) 팀 정보
		List<Map<String, Object>> teamResult = orgService.getOrgTeamTree();

		// 앞에 전사, 사장, 부사장 정보 추가
		Map<String, Object> teamNode1 = new HashMap<>(); // 전사

		teamNode1.put("id", 1);
		teamNode1.put("parent", "#");
		teamNode1.put("text", "전사");
		teamNode1.put("primaryKey", "없음.");
		teamNode1.put("type", "master");
		
		teamResult.add(0, teamNode1);
		
		String teamObj = null;

		try {
			teamObj = new ObjectMapper().writeValueAsString(teamResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// (2) 사원 정보
		List<Map<String, Object>> empResult = orgService.getOrgEmpTree();

		String empObj = null;

		try {
			empObj = new ObjectMapper().writeValueAsString(empResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("teamObj", teamObj);
		model.addAttribute("empObj", empObj);

		return "employee/list";
	}
	
	
	
	// 2-2. 상세 정보(detail)
	@GetMapping("employee/{emp_code}")
	public String selectEmployeeOne(@PathVariable("emp_code") Long emp_code, Model model) {
		
		// (1) 기본 정보
		EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
		
		String formattedRegNo = employeeService.formatEmpRegNo(emp_code);
		
		String empDeptName = employeeService.getDeptNameByEmpCode(emp_code);
		
		// (2) 근태 현황
		List<AttendanceDto> attnDtoList = attendanceService.getAttendancesByEmpCode(emp_code);
		
		// 근무 시간 계산
        for (AttendanceDto temp : attnDtoList) {
            Long working = temp.getWorktime();

            Long workingHour = (working/60) - 1;
            Long workingMinute = working%60;
            
            String calcworkTime = workingHour + "시간 " + workingMinute + "분";
            
            temp.setCalc_worktime(calcworkTime);
        }
		
		model.addAttribute("dto", dto);
		model.addAttribute("formattedRegNo",formattedRegNo);
		model.addAttribute("empDeptName", empDeptName);
		model.addAttribute("attnDtoList", attnDtoList);
		
		
		// (3) 조직도
		List<Map<String, Object>> teamResult = orgService.getOrgTeamTree();

		// 앞에 전사, 사장, 부사장 정보 추가
		Map<String, Object> teamNode1 = new HashMap<>(); // 전사

		teamNode1.put("id", 1);
		teamNode1.put("parent", "#");
		teamNode1.put("text", "전사");
		teamNode1.put("primaryKey", "없음.");
		teamNode1.put("type", "master");
		
		teamResult.add(0, teamNode1);
		
		String teamObj = null;

		try {
			teamObj = new ObjectMapper().writeValueAsString(teamResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// 3. 사원 정보
		List<Map<String, Object>> empResult = orgService.getOrgEmpTree();

		String empObj = null;

		try {
			empObj = new ObjectMapper().writeValueAsString(empResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("teamObj", teamObj);
		model.addAttribute("empObj", empObj);
		
		return "employee/detail";
	}
	
	
	// 3. 정보 수정(update)
	@GetMapping("employeeUpdate/{emp_code}")
	public String updateEmployeePage(@PathVariable("emp_code")Long emp_code, Model model) {
		
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		
		List<com.cc.job.domain.JobDto> jobList = jobService.selectJobList();
		
		EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
		
		String formattedRegNo = employeeService.formatEmpRegNo(emp_code);
		
		String empDeptName = employeeService.getDeptNameByEmpCode(emp_code);
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("jobList", jobList);
		model.addAttribute("dto", dto);
		model.addAttribute("formattedRegNo",formattedRegNo);
		model.addAttribute("empDeptName", empDeptName);
		
		return "employee/update";	
	}
	
	
	// 4. 퇴사 처리(delete)
		@GetMapping("employeeDelete/{emp_code}")
		public String deleteEmployeePage(@PathVariable("emp_code")Long emp_code, Model model) {
//			List<EmpGroupDto> groupList = empGroupService.selectGroupList();
//			
//			List<com.cc.job.domain.JobDto> jobList = jobService.selectJobList();
			
			EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
			
			Long groupNo = employeeService.getGroupNoByEmpCode(emp_code);
			System.out.println(groupNo);
			
			String formattedRegNo = employeeService.formatEmpRegNo(emp_code);
			
			String empDeptName = employeeService.getDeptNameByEmpCode(emp_code);
			
//			model.addAttribute("groupList", groupList);
//			model.addAttribute("jobList", jobList);
			model.addAttribute("dto", dto);
			model.addAttribute("groupNo", groupNo);
			model.addAttribute("formattedRegNo",formattedRegNo);
			model.addAttribute("empDeptName", empDeptName);
			
			return "employee/delete";
		}
		
		// 5. 개인 프로필
		@GetMapping("employeeProfile")
		public String profilePage() {
//			EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
//			
//			String formattedRegNo = employeeService.formatEmpRegNo(emp_code);
//			
//			String empDeptName = employeeService.getDeptNameByEmpCode(emp_code);
//			
//			model.addAttribute("dto", dto);
//			model.addAttribute("formattedRegNo",formattedRegNo);
//			model.addAttribute("empDeptName", empDeptName);
			
			return "employee/profile";
		}
}
