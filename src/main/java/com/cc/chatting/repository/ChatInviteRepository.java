package com.cc.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.chatting.domain.ChatInvite;

public interface ChatInviteRepository extends JpaRepository <ChatInvite, Long>{
	List<ChatInvite> findByEmployeeEmpCode(Long empCode);
}
