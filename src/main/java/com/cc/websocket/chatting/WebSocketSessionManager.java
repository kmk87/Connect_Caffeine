package com.cc.websocket.chatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {
	private Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private Map<String, List<WebSocketSession>> roomClients = new ConcurrentHashMap<>();

    // 특정 사용자 세션 추가
    public void addSession(Long empCode, WebSocketSession session) {
        userSessions.put(empCode, session);
    }

    // 사용자 세션 제거
    public void removeSession(Long empCode) {
        WebSocketSession session = userSessions.remove(empCode);
        roomClients.values().forEach(sessions -> sessions.remove(session));
    }

    // 사용자 세션 가져오기
    public WebSocketSession getSession(Long empCode) {
        return userSessions.get(empCode);
    }

    // 특정 방에 속한 사용자 세션 추가
    public void addSessionToRoom(String roomNo, WebSocketSession session) {
        roomClients.computeIfAbsent(roomNo, k -> new ArrayList<>()).add(session);
    }

    // 특정 방에 속한 사용자 세션들 가져오기
    public List<WebSocketSession> getSessionsInRoom(String roomNo) {
        return roomClients.getOrDefault(roomNo, new ArrayList<>());
    }

    // 특정 방에서 세션 제거
    public void removeSessionFromRoom(String roomNo, WebSocketSession session) {
        List<WebSocketSession> sessions = roomClients.get(roomNo);
        if (sessions != null) {
            sessions.remove(session);
        }
    }
}
