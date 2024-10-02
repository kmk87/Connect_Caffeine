
package com.cc.tree.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.tree.service.OrgService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class OrgApiController {

	private final OrgService orgService;

	// 생성자
	public OrgApiController(OrgService orgService) {
		this.orgService = orgService;
	}

	// 화면 전환
	@GetMapping("/toTree")
	public String toOrganization(Model model) {

		// 1. 팀 정보
		List<Map<String, Object>> teamResult = orgService.getOrgTeamTree();

		// 앞에 전사 정보 추가
		Map<String, Object> teamNode1 = new HashMap<>();

		teamNode1.put("id", 1);
		teamNode1.put("parent", "#");
		teamNode1.put("text", "전사");
		teamNode1.put("primaryKey", "없음.");
		teamNode1.put("type", "전사");

		
		teamResult.add(0, teamNode1);


		String teamObj = null;

		try {
			teamObj = new ObjectMapper().writeValueAsString(teamResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// 2. 사원 정보
		List<Map<String, Object>> empResult = orgService.getOrgEmpTree();

		String empObj = null;

		try {
			empObj = new ObjectMapper().writeValueAsString(empResult);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("teamObj", teamObj);
		model.addAttribute("empObj", empObj);

		return "tree/tree";
	}
}