package com.cc.chatting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.chatting.domain.ChatInviteDto;
import com.cc.chatting.domain.ChatMessageDto;
import com.cc.chatting.domain.ChatRoomDto;
import com.cc.chatting.domain.ChatRoomVo;
import com.cc.chatting.service.ChattingService;

@Controller
public class ChattingViewController {
	
	private final ChattingService chattingService;
	
	@Autowired
	public ChattingViewController(ChattingService chattingService) {
		this.chattingService = chattingService;
	}
	
	@GetMapping("/chatRoomList/{emp_code}")
	public String chatRoomList(Model model,
			@PathVariable("emp_code") Long emp_code) {
		List<ChatRoomVo> roomList = chattingService.selectChatRoomList(emp_code);
//		List<ChatInviteDto> inviteList = chattingService.selectChatInviteList(emp_code);
		
		model.addAttribute("resultList", roomList);
//		model.addAttribute("inviteList", inviteList);
		
		return "chatting/roomList";
	}
	
	@GetMapping("/chatDetail/{room_no}")
	public String chatRoomDetail(Model model,
			@PathVariable("room_no") Long room_no) {
		ChatRoomDto roomResult = chattingService.selectRoomDetail(room_no);
		List<ChatMessageDto> msgResult = chattingService.selectMsgList(room_no);
		System.out.println(roomResult);
		System.out.println(msgResult);
		
		model.addAttribute("roomResult", roomResult);
		model.addAttribute("msgResult", msgResult);
		
		return "chatting/chatDetail";
	}
}
