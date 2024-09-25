package com.cc.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.chatting.domain.ChatInvite;

public interface ChatInviteRepository extends JpaRepository <ChatInvite, Long>{
	List<ChatInvite> findByEmployeeEmpCode(Long empCode);
	
	@Query("SELECT c FROM ChatInvite c WHERE c.chatRoom.roomNo = :roomNo")
	List<ChatInvite> findByChatRoomRoomNo(@Param("roomNo") Long roomNo);
}
