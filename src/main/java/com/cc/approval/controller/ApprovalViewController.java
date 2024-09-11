package com.cc.approval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalService;

@Controller
public class ApprovalViewController {
	
	private final ApprovalService approvalService;
	private final ApprFormService apprFormService;
	
	@Autowired
	public ApprovalViewController(ApprovalService approvalService,ApprFormService apprFormService) {
		this.approvalService = approvalService;
		this.apprFormService = apprFormService;
	}
	
	
	// 기안서 작성
//	@GetMapping("/create")
//	public String getDraftInfoOne(Model model,ApprovalDto approvalDto,ApprFormDto apprFormDto) {
//		Approval dto = approvalService.getDraftInfoOne(approvalDto);
////		ApprFormDto formDto = apprFormService.getDocuOne(apprFormDto);
////		
//		model.addAttribute("dto",dto);
////		model.addAttribute("formDto",formDto);
//		System.out.println("dto : "+dto);
//		return "approval/createVacation";
//	}
	
	
	@GetMapping("/userInfo")
	public String showCreateForm(Model model) {
		// 로그인한 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user =(User)authentication.getPrincipal();
		String memId = user.getUsername();
		System.out.println("memId:"+memId);
		
		model.addAttribute("memId",memId);
		
      return "approval/createVacation"; 
  }
	
	@GetMapping("/dataInfo")
	public String getDataInfo(Model model,ApprovalDto approvalDto, ApprFormDto apprFormDto) {
		ApprovalDto dto = approvalService.getDataInfo(approvalDto);
//		ApprFormDto formDto = apprFormService.getDataInfo(apprFormDto);
	
		model.addAttribute("dto",dto);
//		model.addAttribute("formDto",formDto);
		System.out.println("dto : "+dto);
		return "approval/createVacation";
	}
	
	
	
}
