package com.cc.approval.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalService;




@Controller
public class ApprovalApiController {
	
	private final ApprovalService approvalService;
	private final ApprFormService apprFormService;
	
	@Autowired
	public ApprovalApiController(ApprovalService approvalService,
			ApprFormService apprFormService) {
		this.approvalService = approvalService;
		this.apprFormService = apprFormService;
	}

	
	
	
	@ResponseBody
	@PostMapping("/draft")
	public Map<String,String> createDraft(@RequestBody ApprovalDto dto){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg","결재요청 중 오류가 발생했습니다.");
		
		System.out.println("Api dto: "+dto);
		
		if(approvalService.getDraftInfoOne(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "결재요청이 완료되었습니다.");
		}
		
		return resultMap;
	}
	
	
	
}
