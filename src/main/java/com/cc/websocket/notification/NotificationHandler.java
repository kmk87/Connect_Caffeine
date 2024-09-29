package com.cc.websocket.notification;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cc.employee.domain.Employee;
import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.Notification;
import com.cc.notification.domain.NotificationDto;
import com.cc.notification.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationRepository notificationRepository;
    private final EmployeeService employeeService;
    private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    public NotificationHandler(NotificationRepository notificationRepository, EmployeeService employeeService) {
        this.notificationRepository = notificationRepository;
        this.employeeService = employeeService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // empCode는 문자열로 저장 (Long 타입 대신 String)
        String empCode = session.getPrincipal().getName();  
        
        sessionMap.put(empCode, session);  // empCode를 문자열로 저장
        System.out.println("WebSocket session added for empCode: " + empCode);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String empCode = session.getPrincipal().getName();
        sessionMap.remove(empCode);
        System.out.println("WebSocket connection closed for empCode: " + empCode);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String payload = message.getPayload();
        System.out.println("수신한 메시지: " + payload);

        // 수신한 메시지를 모든 연결된 클라이언트에게 전송 (Broadcast)
        for (WebSocketSession webSocketSession : sessionMap.values()) {
            if (webSocketSession.isOpen() && !webSocketSession.equals(session)) {
                webSocketSession.sendMessage(new TextMessage("Broadcast: " + payload));
            }
        }
    }

    /*WebSocket으로 알림 전송만 처리하는 메서드 */
    public void sendNotification(NotificationDto notificationDto) throws Exception {
        // receiver_no는 사원 코드, 이를 통해 직원의 아이디를 조회
        Employee receiver = employeeService.findByEmpCode(notificationDto.getReceiver_no());  // 사원 코드로 직원 정보 조회
        
        if (receiver != null) {
            String empAccount = receiver.getEmpAccount();  // 직원의 아이디 값 조회
            WebSocketSession session = sessionMap.get(empAccount);  // 아이디 값을 사용해 세션 조회
            
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(notificationDto)));
                System.out.println("Notification sent to empId: " + empAccount);
            } else {
                System.out.println("No WebSocket session found for empId: " + empAccount);
            }
        } else {
            System.out.println("No employee found with empCode: " + notificationDto.getReceiver_no());
        }
    }



    /* 알림을 저장하고 WebSocket으로 전송하는 메서드*/
    public void saveAndSendNotification(NotificationDto notificationDto) throws Exception {
        // Employee 객체 가져오기
        Employee receiver = employeeService.findByEmpCode(notificationDto.getReceiver_no());

        if (receiver == null) {
            System.out.println("Receiver is null for empCode: " + notificationDto.getReceiver_no());
            return; // 수신자가 없으면 알림을 저장하지 않음
        }

        // Notification 엔티티 생성
        Notification notification = Notification.builder()
            .employee(receiver)  // 수신자 설정
            .notificationContent(notificationDto.getNotificationContent())
            .notificationType(notificationDto.getNotificationType())
            .sentTime(LocalDateTime.now())  // 발송 시간 설정
            .isRead('N')
            .relatedLink(notificationDto.getRelatedLink())
            .build();

        // 알림 데이터 저장 (여기서 값 확인)
        System.out.println("Notification: " + notification);
        System.out.println("Notification saved for receiver: " + receiver.getEmpCode());
        System.out.println("Notification message: " + notification.getNotificationContent());
        notificationRepository.save(notification);    

        // WebSocket으로 실시간 알림 전송
        sendNotification(notificationDto);
    }

}
