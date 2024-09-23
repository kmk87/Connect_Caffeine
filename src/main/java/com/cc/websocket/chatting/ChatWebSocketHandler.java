package com.cc.websocket.chatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cc.chatting.domain.ChatMessageDto;
import com.cc.chatting.service.ChattingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler{
	
	private final ChattingService chattingService;
	private Map<String, List<WebSocketSession>> roomClients = new HashMap<>();
	
	@Autowired
	public ChatWebSocketHandler(ChattingService chattingService) {
		this.chattingService = chattingService;
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		
	}
	
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMessageDto msg = objectMapper.readValue(payload, ChatMessageDto.class);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "채팅 전솔중 오류가 발생하였습니다.");
		
		switch(msg.getChat_type()) {
		case "open" :
			roomClients.computeIfAbsent(String.valueOf(msg.getRoom_no()), k -> new ArrayList<>()).add(session);
			break;
		case "msg" :
			if(chattingService.createChatMessage(msg) > 0) {
				resultMap.put("res_code", "200");
				resultMap.put("message_content", msg.getMessage_content());
				resultMap.put("emp_code", String.valueOf(msg.getEmp_code()));
				resultMap.put("roomNo", String.valueOf(msg.getEmp_code()));
				
				TextMessage resultMsg
				= new TextMessage(objectMapper.writeValueAsString(resultMap));
				
				List<WebSocketSession> sessions = roomClients.get(String.valueOf(msg.getRoom_no()));
				if(session != null) {
					for(WebSocketSession clientSession : sessions) {
						if(clientSession.isOpen()) {
							clientSession.sendMessage(resultMsg);
						}
					}
				}
				break;
			}
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		roomClients.values().removeAll(Arrays.asList(session));
		System.out.println("=== 삭제 확인 === ");
		for(String userId : roomClients.keySet()) {
			System.out.println(userId + " : " + roomClients);
		}
	}
}
