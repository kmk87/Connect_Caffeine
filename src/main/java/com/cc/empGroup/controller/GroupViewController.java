package com.cc.empGroup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.domain.EmployeeDto;

@Controller
public class GroupViewController {
	
	private final EmpGroupService empGroupService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupViewController.class);
	
	@Autowired
	public GroupViewController(EmpGroupService empGroupService) {
		this.empGroupService = empGroupService;
	}
	
	@GetMapping("/team/create")
	public String createTeamPage() {
		return "team/create";
	}
	
	// 상세 정보(detail)
	@GetMapping("/teamDetail/{group_no}")
	public String detailTeamPage(Model model, @PathVariable("group_no") Long group_no) {
		EmpGroupDto grDto = empGroupService.selectGroupOne();
		model.addAttributes("grDto", grDto);
		return "team/detail";
	}

}
