package com.cc.empGroup.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;

import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.tree.domain.TreeMenuDto;

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
		
		
		// 3. 수정
		@ResponseBody
		@PostMapping("/empGroupUpdate/{group_no}")
		public Map<String, String> updateGroup(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "팀 정보 수정 중 오류가 발생하였습니다.");

			if (empGroupService.updateGroup(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "팀 정보가 성공적으로 수정되었습니다.");

			}
		
			return resultMap;
		}
		
		// 4. 삭제
		@ResponseBody
		@PostMapping("/empGroupDelete/{group_no}")
		public Map<String, String> deleteGroup(@RequestBody EmpGroupDto dto){
			Map<String, String> resultMap = new HashMap<String, String>();

			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "팀 정보 삭제 중 오류가 발생하였습니다.");

			if (empGroupService.deleteGroup(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "팀 정보가 성공적으로 삭제되었습니다.");

			}
			System.out.println("delete 컨트롤러로 넘어온 객체: "+dto);
			return resultMap;
		}
			
		

}
