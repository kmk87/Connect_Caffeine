package com.cc.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;
import com.cc.group.domain.EmpGroupDto;
import com.cc.group.service.GroupService;
import com.cc.job.service.JobService;

@Controller
public class EmployeeViewController {
	
	private final EmployeeService employeeService;
	private final GroupService groupService;
	private final JobService jobService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeViewController.class);
	
	@Autowired
	public EmployeeViewController(EmployeeService employeeService, GroupService groupService, JobService jobService) {
		this.employeeService = employeeService;
		this.groupService = groupService;
		this.jobService = jobService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "employee/pages-login";
	}
	
	// 사원 등록
	@GetMapping("employeeCreate")
	public String createEmployeePage(Model model) {
		List<EmpGroupDto> groupList = groupService.selectGroupList();
		List<com.cc.job.domain.JobDto> jobList = jobService.selectJobList();
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("jobList", jobList);
		
		return "employee/create";
	}
	
	 //목록
//	@GetMapping("employeeList")
//	public String selectEmployeeList(Model model) {
//		//Page<EmployeeDto> resultList = employeeService.selectEmloyeeList(searchDto, pageable);
////		List<EmployeeDto> empDtoList = employeeService.selectEmployeeList();
//		model.addAttribute("empDtoList", empDtoList);
//		
//		return "employee/list";
//	}
	
	
	// 상세 정보
	@GetMapping("employeeDetail/{emp_code}")
	public String selectEmployeeOne(Model model, @PathVariable("emp_code") Long emp_code) {
		EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
		model.addAttribute("dto", dto);
		return "employee/detail";
	}
	
	@GetMapping("/update")
	public String updateEmployeePage(@PathVariable("emp_code")Long emp_code, Model model) {
			EmployeeDto dto = employeeService.selectEmployeeOne(emp_code);
			model.addAttribute("dte", dto);
			return "employee/update";
	}
}
