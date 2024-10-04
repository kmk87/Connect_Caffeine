package com.cc.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.reservation.domain.BuildingDto;
import com.cc.reservation.service.BuildingService;

@Controller
public class BuildingApiController {

	private final BuildingService buildingService;

	@Autowired
	public BuildingApiController(BuildingService builidngService) {
		this.buildingService = builidngService;
	}
	
	@ResponseBody
	@PostMapping("/buildingCreate")
	public Map<String, String> createBuilding(BuildingDto dto){   
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "건물등록중 오류가 발생했습니다.");
		
		if(buildingService.createBuilding(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "건물이 성공적으로 등록되었습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/buildingUpdate")
	public Map<String, String> updateBuilding(BuildingDto dto){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "건물수정중 오류가 발생했습니다.");
		
		if(buildingService.updateBuilding(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "건물이 성공적으로 수정되었습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/building_delete/{buildingNo}")
	public ResponseEntity<Map<String, String>> deleteBuilding(@PathVariable("buildingNo") Long buildingNo){
		Map<String, String> resultMap = new HashMap<String, String>();
		 try {
	            boolean isDeleted = buildingService.deleteBuilding(buildingNo);
	            if (isDeleted) {
	                resultMap.put("res_code", "200");
	                resultMap.put("res_msg", "건물이 성공적으로 삭제되었습니다.");
	            } else {
	                resultMap.put("res_code", "500");
	                resultMap.put("res_msg", "건물 삭제에 실패했습니다.");
	            }
	        } catch (Exception e) {
	            resultMap.put("res_code", "500");
	            resultMap.put("res_msg", "서버 오류가 발생했습니다.");
	        }
	        return ResponseEntity.ok(resultMap);
	    }
}
