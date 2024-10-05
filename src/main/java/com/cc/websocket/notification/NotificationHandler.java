package com.cc.websocket.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    // empAccount 당 여러 개의 WebSocketSession을 관리할 수 있도록 변경
    private Map<String, List<WebSocketSession>> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    public NotificationHandler(NotificationRepository notificationRepository, EmployeeService employeeService) {
        this.notificationRepository = notificationRepository;
        this.employeeService = employeeService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String empAccount = session.getPrincipal().getName();

        // 해당 empAccount에 대한 세션 리스트 가져오기
        sessionMap.computeIfAbsent(empAccount, k -> new ArrayList<>()).add(session);
        System.out.println("WebSocket session added for empAccount: " + empAccount);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String empAccount = session.getPrincipal().getName();
        
        // 해당 empAccount에 연결된 세션 리스트에서 제거
        List<WebSocketSession> sessions = sessionMap.get(empAccount);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                sessionMap.remove(empAccount);
            }
        }
        System.out.println("WebSocket connection closed for empAccount: " + empAccount);
    }
    public void sendNotification(NotificationDto notificationDto) throws Exception {
        Employee receiver = employeeService.findByEmpCode(notificationDto.getReceiver_no());

        if (receiver != null) {
            String empAccount = receiver.getEmpAccount();
            List<WebSocketSession> sessions = sessionMap.get(empAccount);

            if (sessions != null && !sessions.isEmpty()) {
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(notificationDto)));
                        System.out.println("Notification sent to empAccount: " + empAccount);
                    }
                }
            } else {
                System.out.println("No WebSocket session found for empAccount: " + empAccount);
            }
        } else {
            System.out.println("No employee found with empCode: " + notificationDto.getReceiver_no());
        }
    }

    /* 알림 저장 후 WebSocket으로 전송 */
    public void saveAndSendNotification(NotificationDto notificationDto) throws Exception {
        Employee receiver = employeeService.findByEmpCode(notificationDto.getReceiver_no());

        if (receiver == null) {
            System.out.println("Receiver is null for empCode: " + notificationDto.getReceiver_no());
            return;
        }

        Notification notification = Notification.builder()
            .employee(receiver)
            .notificationContent(notificationDto.getNotificationContent())
            .notificationType(notificationDto.getNotificationType())
            .sentTime(LocalDateTime.now())
            .isRead('N')
            .relatedLink(notificationDto.getRelatedLink())
            .build();

        notificationRepository.save(notification);

        // WebSocket으로 실시간 알림 전송
        sendNotification(notificationDto);
    }
}