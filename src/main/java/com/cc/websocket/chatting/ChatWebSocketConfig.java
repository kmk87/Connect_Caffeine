package com.cc.websocket.chatting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer{
	
	private final ChatWebSocketHandler chatWebSocketHandler;
	
	@Autowired
	public ChatWebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
		this.chatWebSocketHandler = chatWebSocketHandler;
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry
			.addHandler(chatWebSocketHandler, "/chatting")
			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setAllowedOrigins("*");
	}
	
}
