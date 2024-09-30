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
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationHandler extends TextWebSocketHandler {

    

}