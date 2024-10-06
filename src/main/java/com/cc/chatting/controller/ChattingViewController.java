package com.cc.chatting.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.chatting.domain.ChatMessageDto;
import com.cc.chatting.domain.ChatRoomDto;
import com.cc.chatting.domain.ChatRoomVo;
import com.cc.chatting.service.ChattingService;
import com.cc.tree.service.OrgService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChattingViewController {

	private final ChattingService chattingService;

	private final OrgService orgService;

	@Autowired
	public ChattingViewController(ChattingService chattingService,
			OrgService orgService) {
		this.chattingService = chattingService;
		this.orgService = orgService;
	}

	@GetMapping("/chatRoomList/{emp_code}")
	public String chatRoomList(HttpServletRequest request, Model model, @PathVariable("emp_code") Long emp_code) {
		List<ChatRoomVo> roomList = chattingService.selectChatRoomList(emp_code);
//		List<ChatInviteDto> inviteList = chattingService.selectChatInviteList(emp_code);
		String currentUri = request.getRequestURI();
		model.addAttribute("currentUri", currentUri);
		System.out.println("Current URI: " + request.getRequestURI());

		model.addAttribute("resultList", roomList);
//		model.addAttribute("inviteList", inviteList);

		return "chatting/roomList";
	}

	@ResponseBody
	@GetMapping("/getOrgChartChat")
	public ResponseEntity<List<Map<String, Object>>> getOrgChart() {
	    List<Map<String, Object>> orgNodes = orgService.getOrgTree();  // 조직도 데이터를 가져오는 메서드
	    return ResponseEntity.ok(orgNodes);  // JSON 형태로 반환
	}
	

	@GetMapping("/chatDetail/{room_no}")
	public String chatRoomDetail(Model model, @PathVariable("room_no") Long room_no) {
		ChatRoomDto roomResult = chattingService.selectRoomDetail(room_no);
		List<ChatMessageDto> msgResult = chattingService.selectMsgList(room_no);
		System.out.println(roomResult);
		System.out.println(msgResult);

		model.addAttribute("roomResult", roomResult);
		model.addAttribute("msgResult", msgResult);

		return "chatting/chatDetail";
	}
}
