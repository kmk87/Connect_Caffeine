package com.cc.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.reservation.domain.MeetingRoom;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.service.MeetingRoomService;

@Controller
public class MeetingRoomApiController {

	private final MeetingRoomService meetingRoomService;
	
	
	@Autowired
	public MeetingRoomApiController(MeetingRoomService meetingRoomService) {
		this.meetingRoomService = meetingRoomService;
	}
	
	@ResponseBody
	@PostMapping("/meeting_room")
	public Map<String ,String> createMeetingRoom(@RequestBody MeetingRoomDto dto){
	    Map<String, String> resultMap = new HashMap<>();
	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "회의실 등록 중 오류가 발생했습니다.");

	    if(meetingRoomService.createMeetingRoom(dto) != null) {
	        resultMap.put("res_code", "200");
	        resultMap.put("res_msg", "회의실이 성공적으로 등록되었습니다.");
	    }

	    return resultMap;
	}
	
}
