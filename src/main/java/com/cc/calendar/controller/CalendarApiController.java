package com.cc.calendar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public Map<String, Object> createSchedule(@RequestBody CalendarDto dto) {

	    Map<String, Object> resultMap = new HashMap<>();
	    
	    // 일정 등록 로직
	    if (calendarService.createSchedule(dto) != null) {
	    	resultMap.put("schedule_no", dto.getSchedule_no());
	        resultMap.put("schedule_title", dto.getSchedule_title());
	        resultMap.put("start_time", dto.getStart_time());
	        resultMap.put("end_time", dto.getEnd_time());
	        resultMap.put("schedule_content", dto.getSchedule_content());
	        resultMap.put("location", dto.getLocation());
	        resultMap.put("res_code", "200");
	        resultMap.put("res_msg", "일정이 성공적으로 등록되었습니다.");
	    } else {
	        resultMap.put("res_code", "404");
	        resultMap.put("res_msg", "일정 등록 중 오류가 발생하였습니다.");
	    }
	    
	    return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/calendarEdit/{schedule_no}")
	public Map<String, String> updateSchedule(@RequestBody CalendarDto dto) { 
	    Map<String, String> resultMap = new HashMap<>();

	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "일정 수정 중 오류가 발생했습니다.");
	    
	    if (calendarService.updateSchedule(dto) != null) {
	        resultMap.put("res_code", "200");
	        resultMap.put("res_msg", "정상적으로 일정이 수정되었습니다.");
	    }
	    return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/calendarDelete/{schedule_no}")
	public Map<String,String> deleteSchedule(@PathVariable("schedule_no") Long schedule_no){
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "게시글 삭제중 오류가 발생했습니다.");
		
		
		if(calendarService.deleteSchedule(schedule_no) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "정상적으로 게시글이 삭제되었습니다.");
			
		}
		return map;
	}

}
