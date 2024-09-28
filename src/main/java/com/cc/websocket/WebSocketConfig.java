package com.cc.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cc.websocket.chatting.ChattingHandler;
import com.cc.websocket.notification.NotificationHandler;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final NotificationHandler notificationHandler;
    private final ChattingHandler chattingHandler;

    @Autowired
    public WebSocketConfig(NotificationHandler notificationHandler,ChattingHandler chattingHandler) {
        this.notificationHandler = notificationHandler;
        this.chattingHandler = chattingHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler, "/ws/notifications").setAllowedOrigins("*");
        registry.addHandler(chattingHandler, "/ws/chat").setAllowedOrigins("*");
    }
}