package com.cc.chatting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.chatting.domain.ChatInvite;
import com.cc.chatting.domain.ChatMessage;
import com.cc.chatting.domain.ChatMessageDto;
import com.cc.chatting.domain.ChatRoom;
import com.cc.chatting.domain.ChatRoomDto;
import com.cc.chatting.domain.ChatRoomVo;
import com.cc.chatting.mapper.ChatRoomMapper;
import com.cc.chatting.repository.ChatInviteRepository;
import com.cc.chatting.repository.ChatMessageRepository;
import com.cc.chatting.repository.ChatRoomRepository;

@Service
public class ChattingService {
	
	private final ChatRoomMapper chatRoomMapper;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatInviteRepository chatInviteRepository;
	private final ChatMessageRepository chatMessageRepository;
	
	@Autowired
	public ChattingService(ChatRoomRepository chatRoomRepository,
			ChatInviteRepository chatInviteRepository,
			ChatRoomMapper chatRoomMapper,
			ChatMessageRepository chatMessageRepository) {
		this.chatRoomRepository = chatRoomRepository;
		this.chatInviteRepository = chatInviteRepository;
		this.chatRoomMapper = chatRoomMapper;
		this.chatMessageRepository = chatMessageRepository;
	}
	
	public List<ChatRoomVo> selectChatRoomList(Long emp_code){
		List<ChatInvite> inviteList = chatInviteRepository.findByEmployeeEmpCode(emp_code);
		
		List<Long> roomNoList = new ArrayList<>();
        for (ChatInvite rnl : inviteList) {
        	roomNoList.add(rnl.getChatRoom().getRoomNo());
        }
        
		List<ChatRoomVo> roomList = chatRoomMapper.selectChatRoomList(roomNoList);
		
		return roomList;
	}
	
	public ChatRoomDto selectRoomDetail(Long room_no) {
		ChatRoom roomResult = chatRoomRepository.findByroomNo(room_no);
		ChatRoomDto dto = ChatRoomDto.builder()
				.room_no(roomResult.getRoomNo())
				.room_name(roomResult.getRoomName())
				.last_msg(roomResult.getLastMsg())
				.last_date(roomResult.getLastDate())
				.build();
		return dto;
	}
	
	public List<ChatMessageDto> selectMsgList(Long room_no){
		List<ChatMessage> msgList = chatMessageRepository.findByChatInviteChatRoomRoomNoOrderByMessageDateAsc(room_no);
		List<ChatMessageDto> msgdtoList = new ArrayList<ChatMessageDto>();
		
		for(ChatMessage cm : msgList) {
			ChatMessageDto dto = new ChatMessageDto().toDto(cm);
			msgdtoList.add(dto);
		}
		
		return msgdtoList;
	}
}
