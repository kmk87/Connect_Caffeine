package com.cc.calendar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.service.CalendarService;

@Controller
public class CalendarApiController {
	
	private final CalendarService calendarService;
	@Autowired
	public CalendarApiController(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	
	 @PostMapping("/calendar")
	 @ResponseBody
	 public Map<String, String> createSchedule(@RequestBody CalendarDto dto) {
	     System.out.println("Received schedule data: " + dto); // 로그로 데이터 확인
	     Map<String, String> resultMap = new HashMap<>();
	     
	     resultMap.put("res_code", "404");
	     resultMap.put("res_msg", "일정 등록 중 오류가 발생하였습니다.");

	     if (calendarService.createSchedule(dto) != null) {
	         resultMap.put("res_code", "200");
	         resultMap.put("res_msg", "일정이 성공적으로 등록되었습니다.");
	     }

	     return resultMap;
	 }

	
	
}
