package com.cc.websocket.chatting;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ChattingHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChattingHandler.class);

    private final ChattingService chattingService;
    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper;
    private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Autowired
    public ChattingHandler(ChattingService chattingService, WebSocketSessionManager sessionManager) {
        this.chattingService = chattingService;
        this.sessionManager = sessionManager;
        this.objectMapper = new ObjectMapper(); // ObjectMapper를 클래스 필드로 선언
    }

    // WebSocket 연결이 성공했을 때 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String empCode = session.getPrincipal().getName(); // 세션에서 empCode 추가
        System.out.println("empCode : " + empCode);
        
        sessionMap.put(empCode, session);
       }

    // WebSocket으로부터 텍스트 메시지를 받았을 때 호출되는 메서드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto msg = objectMapper.readValue(payload, ChatMessageDto.class);
        
        Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "채팅 전송중 오류가 발생하였습니다.");
        
        // 메시지 타입에 따라 처리
        switch (msg.getChat_type()) {
            case "open":
                // 사용자를 특정 채팅방에 추가
                sessionMap.put(msg.getEmp_code().toString(), session);
                break;

            case "msg":
                // 메시지 전송 및 저장 로직 처리
                if(chattingService.createChatMessage(msg) > 0) {
                	resultMap.put("res_code", "200");
                	resultMap.put("message_content", msg.getMessage_content());
                	resultMap.put("room_no", String.valueOf(msg.getRoom_no()));
                	resultMap.put("emp_code", String.valueOf(msg.getEmp_code()));
                	
                	TextMessage resultMsg
                	= new TextMessage(objectMapper.writeValueAsString(resultMap));
                	
                	for(String id : sessionMap.keySet()) {
                		WebSocketSession client = sessionMap.get(id);
                		client.sendMessage(resultMsg);
                	}
                	
                }
            	
            	
                System.out.println("메세시 전송 : " + msg);
                break;

            default:
                // 정의되지 않은 메시지 타입 처리
                throw new IllegalArgumentException("알 수 없는 메시지 타입: " + msg.getChat_type());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 종료되더라도 강제로 세션을 닫지 않음.
        // 만약 특정 조건에서만 연결을 종료하고 싶다면, 조건을 추가할 수 있음.

        String empCode = session.getPrincipal().getName(); // 세션에서 empCode 추출
        if (empCode != null) {
            logger.info("WebSocket 연결 종료 시도됨: empCode={}, sessionId={}", empCode, session.getId());
            // 여기서 세션을 종료하지 않음
        } else {
            logger.warn("WebSocket 연결 종료 시 empCode가 null입니다. sessionId={}", session.getId());
        }
    }

//    // 세션에서 empCode 추출
//    private String getEmpCodeFromSession(WebSocketSession session) {
//        // 세션에서 empCode를 추출하는 로직 (Principal 또는 Attributes를 사용)
//        Map<String, Object> attributes = session.getAttributes();
//        if (attributes != null && attributes.containsKey("empCode")) {
//        	System.err.println("attributes : " + attributes);
//            return attributes.get("empCode").toString();
//        }
//        logger.warn("WebSocket 세션에 empCode가 없습니다. sessionId={}", session.getId());
//        return null; // empCode가 없을 경우 null 반환
//    }

    // 메시지 전송 및 저장 처리
//    private void handleMessage(WebSocketSession session, ChatMessageDto msg) throws Exception {
//        // 채팅 메시지 DB 저장
//        if (chattingService.createChatMessage(msg) > 0) {
//            // 방에 속한 모든 세션에게 메시지를 전송
//            List<WebSocketSession> sessions = sessionManager.getSessionsInRoom(String.valueOf(msg.getRoom_no()));
//            TextMessage resultMsg = new TextMessage(objectMapper.writeValueAsString(msg));
//
//            for (WebSocketSession clientSession : sessions) {
//                if (clientSession.isOpen()) {
//                    clientSession.sendMessage(resultMsg);
//                }
//            }
//            logger.info("메시지 전송 완료: roomNo={}, messageContent={}", msg.getRoom_no(), msg.getMessage_content());
//        } else {
//            logger.error("메시지 저장 중 오류 발생: roomNo={}, messageContent={}", msg.getRoom_no(), msg.getMessage_content());
//        }
//    }
}
