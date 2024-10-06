package com.cc.calendar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.calendar.domain.UserScheduleColorDto;
import com.cc.calendar.service.ColorService;
import com.cc.calendar.service.UserScheduleColorService;



@Controller
public class ColorApiController {

	private final ColorService colorService;
	private final UserScheduleColorService userScheduleColorService;
	
	@Autowired
	public ColorApiController(ColorService colorService,UserScheduleColorService userScheduleColorService) {
		this.colorService = colorService;
		this.userScheduleColorService = userScheduleColorService;
	}
	


	@ResponseBody
	@PostMapping("/updateColor")
	public Map<String, String> updateSchedule(@RequestBody UserScheduleColorDto dto) { 
	    Map<String, String> resultMap = new HashMap<>();
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "컬러 수정 중 오류가 발생했습니다.");

	    // dto의 값이 제대로 들어왔는지 확인
//	    System.out.println("Received DTO: " + dto);

	    if (userScheduleColorService.updateColorForScheduleType(dto) != null) {
	        resultMap.put("res_code", "200");
	        resultMap.put("res_msg", "정상적으로 컬러가 수정되었습니다.");
	    }
	    return resultMap;
	}



}
