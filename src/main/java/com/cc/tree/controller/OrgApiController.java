package com.cc.tree.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.tree.service.OrgService;

@Controller
public class OrgApiController {

	private final OrgService orgService;

    // 생성자
    public OrgApiController(OrgService orgService) {
        this.orgService = orgService;
    }

	// 화면 전환
	@GetMapping("/toOrganization")
	public String toOrganization(Model model) {
		List<Map <String, Object>> result = orgService.getOrgTree();
		System.out.println(result);
		model.addAttribute("treeResult", result);
		
		return "notice/Organization";
	}
    
    

}