package com.cc.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.job.service.JobService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmployeeViewController {
	
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private final JobService jobService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeViewController.class);
	
	@Autowired
	public EmployeeViewController(EmployeeService employeeService, EmpGroupService empGroupService, JobService jobService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
		this.jobService = jobService;
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
	public String selectEmployeeList(HttpServletRequest request,Model model) {
		
		List<EmployeeDto> empDtoList = employeeService.selectEmployeeList();
		String currentUri = request.getRequestURI();
		model.addAttribute("currentUri", currentUri);

		model.addAttribute("empDtoList", empDtoList);
		
		return "employee/list";
	}
	
	
	// 2-2. 상세 정보(detail)
	@GetMapping("employee/{emp_code}")
	public String selectEmployeeOne(@PathVariable("emp_code") Long emp_code, Model model) {
		
		EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
		
		String formattedRegNo = employeeService.formatEmpRegNo(emp_code);
		
		String empDeptName = employeeService.getDeptNameByEmpCode(emp_code);
		
		model.addAttribute("dto", dto);
		model.addAttribute("formattedRegNo",formattedRegNo);
		model.addAttribute("empDeptName", empDeptName);
		
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
