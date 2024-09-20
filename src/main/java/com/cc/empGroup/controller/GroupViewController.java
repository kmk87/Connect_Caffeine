package com.cc.empGroup.controller;

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
import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.service.EmployeeService;

@Controller
public class GroupViewController {
	
	private final EmployeeService employeeService;
	private final EmpGroupService empGroupService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupViewController.class);
	
	@Autowired
	public GroupViewController(EmployeeService employeeService, EmpGroupService empGroupService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
	}
	
	// 팀 등록(create)
	@GetMapping("/empGroupCreate")
	public String createGroupPage(Model model) {
		List<EmployeeDto> empList = employeeService.selectEmployeeList();
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		
		System.out.println(empList);
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("empList", empList);
		
		return "empGroup/create";
	}
	
	
	// 상세 정보(detail)
	@GetMapping("/empGroup/{group_no}")
	public String detailTeamPage(Model model, @PathVariable("group_no") Long group_no) {
		
		EmpGroupDto egDto = empGroupService.selectGroupOne(group_no);
		
		// 책임자 정보 가져오기
		Employee leader = empGroupService.getLeaderInfoByGroupNo(group_no);
		String leaderName = leader.getEmpName();
		String leaderDeskPhone = leader.getEmpDeskPhone();
		
		// 부서와 팀일 때 나누어서 보내기
		if(egDto.getGroup_level().equals("G1")) { // 부서일 때
			// 부서명 가져오기
			String deptOri = empGroupService.getDeptOriNameByGroupNo(group_no);
			
			// 인원 수 가져오기
			Long deptHeadcount = empGroupService.getDeptHeadcountByGroupNo(group_no);
			
			model.addAttribute("deptOri", deptOri);
			model.addAttribute("deptHeadcount", deptHeadcount);
			
		} else if(egDto.getGroup_level().equals("G2")) { // 팀일 때
			// 상위 부서명 가져오기
			String parentDeptName = empGroupService.getParentDeptNameByGroupNo(group_no);
			
			model.addAttribute("parentDeptName", parentDeptName);
			
		}
		
		model.addAttribute("egDto", egDto);
		model.addAttribute("leaderName", leaderName);
		model.addAttribute("leaderDeskPhone", leaderDeskPhone);
		
		return "empGroup/detail";
	}
	
	
	// 목록(list)
	@GetMapping("/empGroupList")
	public String selectEmpGroupList(Model model) {
		
		List<EmpGroupDto> empGroupDtoList = empGroupService.selectGroupList();	
		
		model.addAttribute("empGroupDtoList", empGroupDtoList);
		
		return "empGroup/list";
	}

}
