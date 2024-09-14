package com.cc.empGroup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.cc.group.service.GroupService;

@Controller
public class GroupViewController {
	
	private final GroupService groupService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupViewController.class);
	
	@Autowired
	public GroupViewController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping("/team/create")
	public String createTeamPage() {
		return "team/create";
	}
	
	
	@GetMapping("/team/detail")
	public String detailEmployeePage() {
		return "team/detail";
	}
}
