package com.cc.websocket.chatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final WebSocketSessionManager sessionManager;

    @Autowired
    public ChatWebSocketHandler(ChattingService chattingService, WebSocketSessionManager sessionManager) {
        this.chattingService = chattingService;
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long empCode = getEmpCodeFromSession(session); // 세션에서 empCode 추출 로직 구현 필요
        sessionManager.addSession(empCode, session);
        System.out.println("WebSocket 연결됨: " + empCode);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto msg = objectMapper.readValue(payload, ChatMessageDto.class);

        switch (msg.getChat_type()) {
            case "open":
                // 특정 방에 세션 추가
                sessionManager.addSessionToRoom(String.valueOf(msg.getRoom_no()), session);
                break;
            case "msg":
                // 메시지 전송 및 저장 로직
                if (chattingService.createChatMessage(msg) > 0) {
                    // 방에 속한 사용자들에게 메시지 전송
                    List<WebSocketSession> sessions = sessionManager.getSessionsInRoom(String.valueOf(msg.getRoom_no()));
                    TextMessage resultMsg = new TextMessage(objectMapper.writeValueAsString(msg));
                    for (WebSocketSession clientSession : sessions) {
                        if (clientSession.isOpen()) {
                            clientSession.sendMessage(resultMsg);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long empCode = getEmpCodeFromSession(session);
        sessionManager.removeSession(empCode);
        System.out.println("WebSocket 연결 종료: " + empCode);
    }

    private Long getEmpCodeFromSession(WebSocketSession session) {
        // 세션에서 사용자 ID(empCode)를 추출하는 로직 구현 필요
        return Long.valueOf(session.getAttributes().get("empCode").toString());
    }
}
