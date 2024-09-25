package com.cc.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.chatting.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository <ChatMessage, Long>{
//	List<ChatMessage> findByChatInviteChatRoomRoomNoOrderByMessageDateAsc(Long roomNo);
	List<ChatMessage> findByRoomNoOrderByMessageDateAsc(Long roomNo);
}
