package com.cc.empGroup.controller;


import java.util.List;

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
	
	@Autowired
	public GroupViewController(EmployeeService employeeService, EmpGroupService empGroupService) {
		this.employeeService = employeeService;
		this.empGroupService = empGroupService;
	}
	
	// 1-1. 부서 등록(create)
	@GetMapping("/empDeptCreate")
	public String createDeptPage(Model model) {
		
		List<EmployeeDto> empList = employeeService.selectEmployeeList();
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		
		model.addAttribute("empList", empList);
		model.addAttribute("groupList", groupList);
		
		return "empGroup/deptCreate";
	}
	
	
	// 1-2. 팀 등록(create)
	@GetMapping("/empTeamCreate")
	public String createTeamPage(Model model) {
		
		List<EmployeeDto> empList = employeeService.selectEmployeeList();
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		
		model.addAttribute("empList", empList);
		model.addAttribute("groupList", groupList);

		return "empGroup/teamCreate";
	}
	
	
	// 2-1. 목록(list)
	@GetMapping("/empGroupList")
	public String selectEmpGroupList(Model model) {
		
		List<EmpGroupDto> empGroupDtoList = empGroupService.selectGroupList();	
		
		model.addAttribute("empGroupDtoList", empGroupDtoList);
		
		return "empGroup/list";
	}
	
	
	// 2-2. 상세 정보(detail)
	@GetMapping("/empGroup/{group_no}")
	public String detailTeamPage(@PathVariable("group_no") Long group_no, Model model) {
		
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
	
	
	
	// 3-1. 부서 수정
	@GetMapping("/empDeptUpdate/{group_no}")
	public String updateDept(@PathVariable("group_no")Long group_no, Model model) {
		
		EmpGroupDto egDto = empGroupService.selectGroupOne(group_no);
		List<EmployeeDto> empList = employeeService.selectEmployeeList();
		List<EmpGroupDto> groupList = empGroupService.selectGroupList();
		
		// 부서 인원 가져오기
		Long deptHeadcount = empGroupService.getDeptHeadcountByGroupNo(group_no);
		
		// 책임자 정보 가져오기
		Employee leader = empGroupService.getLeaderInfoByGroupNo(group_no);
		String leaderName = leader.getEmpName();
		String leaderDeskPhone = leader.getEmpDeskPhone();
		
		
		model.addAttribute("egDto", egDto);
		model.addAttribute("empList", empList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("deptHeadcount", deptHeadcount);
		model.addAttribute("leaderName", leaderName);
		model.addAttribute("leaderDeskPhone", leaderDeskPhone);
		
		return "empGroup/deptUpdate";
	}
	
	
	// 3-2. 팀 수정
		@GetMapping("/empTeamUpdate/{group_no}")
		public String updateTeam(@PathVariable("group_no")Long group_no, Model model) {
			
			EmpGroupDto egDto = empGroupService.selectGroupOne(group_no);
			List<EmployeeDto> empList = employeeService.selectEmployeeList();
			List<EmpGroupDto> groupList = empGroupService.selectGroupList();
			
			// 부서 인원 가져오기
			Long deptHeadcount = empGroupService.getDeptHeadcountByGroupNo(group_no);
			
			// 책임자 정보 가져오기
			Employee leader = empGroupService.getLeaderInfoByGroupNo(group_no);
			String leaderName = leader.getEmpName();
			String leaderDeskPhone = leader.getEmpDeskPhone();
			
			
			model.addAttribute("egDto", egDto);
			model.addAttribute("empList", empList);
			model.addAttribute("groupList", groupList);
			model.addAttribute("deptHeadcount", deptHeadcount);
			model.addAttribute("leaderName", leaderName);
			model.addAttribute("leaderDeskPhone", leaderDeskPhone);
			
			return "empGroup/teamUpdate";
		}
	
	
	// 4. 삭제(delete)
	

}
