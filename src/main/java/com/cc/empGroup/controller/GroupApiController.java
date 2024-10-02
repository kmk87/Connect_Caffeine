package com.cc.empGroup.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;

import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.service.EmpGroupService;

@Controller
public class GroupApiController {
	private EmpGroupService empGroupService;
	
	@Autowired
	public GroupApiController(EmpGroupService empGroupService) {
		this.empGroupService = empGroupService;
	}
	
	// 1. 등록
		@ResponseBody
		@PostMapping("/empGroup")
		public Map<String, String> createGroup(@RequestBody EmpGroupDto dto){
			
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "팀 정보 등록 중 오류가 발생하였습니다.");
			
			if(empGroupService.createGroup(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "팀 정보가 성공적으로 등록되었습니다.");
			}
			
			return resultMap;
		}
		
		// 3-1. 부서 수정
		@ResponseBody
		@PostMapping("/deptUpdate/{group_no}")
		public Map<String, String> updateDept(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "부서 정보 수정 중 오류가 발생하였습니다.");

			if (empGroupService.updateGroup(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "부서 정보가 성공적으로 수정되었습니다.");

			}
			return resultMap;
		}
		
		
		// 3-2. 팀 수정
		@ResponseBody
		@PostMapping("/teamUpdate/{group_no}")
		public Map<String, String> updateTeam(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "팀 정보 수정 중 오류가 발생하였습니다.");

			if (empGroupService.updateGroup(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "팀 정보가 성공적으로 수정되었습니다.");

			}
			return resultMap;
		}
		
		
		
		// 4-1. 팀 삭제
		@ResponseBody
		@PostMapping("/empDeptDelete/{group_no}")
		public Map<String, String> deleteDept(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "부서 정보 삭제 중 오류가 발생하였습니다.");

			if (empGroupService.deleteDept(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "부서 정보가 성공적으로 삭제되었습니다.");

			}
			return resultMap;
		}
		
		
		// 4-2. 팀 삭제
		@ResponseBody
		@PostMapping("/empTeamDelete/{group_no}")
		public Map<String, String> deleteTeam(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "팀 정보 삭제 중 오류가 발생하였습니다.");

			if (empGroupService.deleteTeam(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "팀 정보가 성공적으로 삭제되었습니다.");

			}
			return resultMap;
		}
	
}
