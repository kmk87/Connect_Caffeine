package com.cc.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeViewController {
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "member/pages-login";
	}
}
