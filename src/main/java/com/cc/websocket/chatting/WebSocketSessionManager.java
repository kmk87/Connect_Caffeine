package com.cc.websocket.chatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {

	// 방 번호별 세션 리스트 관리 (roomNo -> List of WebSocketSession)
    private Map<String, List<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    // 특정 방에 세션 추가
    public void addSessionToRoom(String roomNo, WebSocketSession session) {
        roomSessions.computeIfAbsent(roomNo, key -> new ArrayList<>()).add(session);
    }

    // 특정 방에서 세션 제거
    public void removeSessionFromRoom(String roomNo, WebSocketSession session) {
        List<WebSocketSession> sessions = roomSessions.get(roomNo);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(roomNo);
            }
        }
    }

    // 특정 방의 모든 세션을 가져오기
    public List<WebSocketSession> getSessionsInRoom(String roomNo) {
        return roomSessions.getOrDefault(roomNo, new ArrayList<>());
    }

    // 사용자별 세션 관리 (기존 코드와 연결해서 사용 가능)
    private Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        userSessions.put(userId, session);
    }

    public void removeSession(String userId) {
        userSessions.remove(userId);
    }

    public WebSocketSession getSession(String userId) {
        return userSessions.get(userId);
    }
}
