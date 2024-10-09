package com.cc.chatting.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.chatting.domain.ChatInvite;
import com.cc.chatting.domain.ChatRoom;
import com.cc.chatting.domain.ChatRoomDto;
import com.cc.chatting.service.ChattingService;

@Controller
public class ChattingApiController {

	private final ChattingService chattingService;

	@Autowired
	public ChattingApiController(ChattingService chattingService) {
		this.chattingService = chattingService;
	}

	@ResponseBody
	@PostMapping("/chatRoomCreate")
	public Map<String, String> chatRoomCreate(@RequestBody ChatRoomDto dto) {
		System.out.println(dto);
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "채팅방 생성중 오류가 발생하였습니다.");

		
		  Long roomNo = chattingService.createChatRoom(dto.getRoom_name()); 
		  int chatInvite = chattingService.inviteChatRoom(roomNo, dto.getEmp_code());
		  
		  if(chatInvite > 0) {
			  resultMap.put("res_code", "200");
			  resultMap.put("res_msg", "정상적으로 채팅방이 생성 되었습니다.");
		  }

		return resultMap;
	}
}
