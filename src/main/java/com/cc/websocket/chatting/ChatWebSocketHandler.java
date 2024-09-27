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
        Long empCode = getEmpCodeFromSession(session);       // 세션에서 Long 타입의 empCode 추출
        sessionManager.addSession(empCode.toString(), session);  // empCode를 String으로 변환하여 세션 등록
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
        Long empCode = getEmpCodeFromSession(session);       // 세션에서 Long 타입의 empCode 추출
        sessionManager.removeSession(empCode.toString());    // empCode를 String으로 변환하여 세션 삭제
    }

    private Long getEmpCodeFromSession(WebSocketSession session) {
        // 예시로 HttpSession에서 가져오는 방식 (프로젝트에 맞게 수정)
        Map<String, Object> attributes = session.getAttributes();
        return (Long) attributes.get("empCode");  // empCode를 Long으로 캐스팅
    }
}
