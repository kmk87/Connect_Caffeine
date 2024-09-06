package com.cc.approval.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApprovalViewController {
	
	// 기안서 작성
	@GetMapping("/draft")
	public String draftPage() {
		return "approval/create";
	}
}
