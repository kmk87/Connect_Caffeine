package com.cc.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.chatting.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
	ChatRoom findByroomNo(Long roomNo);
}
