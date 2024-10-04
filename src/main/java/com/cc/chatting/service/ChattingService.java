package com.cc.chatting.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.websocket.chatting.WebSocketSessionManager;

@Service
public class ChattingService {
	private final WebSocketSessionManager sessionManager;
	private final ChatRoomMapper chatRoomMapper;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatInviteRepository chatInviteRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final EmployeeRepository employeeRepository;
	
	private Map<String, List<WebSocketSession>> roomClients = new ConcurrentHashMap<>();
	
	@Autowired
	public ChattingService(ChatRoomRepository chatRoomRepository,
			ChatInviteRepository chatInviteRepository,
			ChatRoomMapper chatRoomMapper,
			ChatMessageRepository chatMessageRepository,
			WebSocketSessionManager sessionManager,
			EmployeeRepository employeeRepository) {
		this.chatRoomRepository = chatRoomRepository;
		this.chatInviteRepository = chatInviteRepository;
		this.chatRoomMapper = chatRoomMapper;
		this.chatMessageRepository = chatMessageRepository;
		this.sessionManager = sessionManager;
		this.employeeRepository = employeeRepository;
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
		List<ChatMessage> msgList = chatMessageRepository.findByRoomNoOrderByMessageDateAsc(room_no);
		List<ChatMessageDto> msgdtoList = new ArrayList<ChatMessageDto>();
		for(ChatMessage cm : msgList) {
			ChatMessageDto dto = new ChatMessageDto().toDto(cm);
			
			Employee employee = employeeRepository.findByempCode(cm.getEmpCode());
			dto.setEmp_name(employee.getEmpName());
			
			msgdtoList.add(dto);
		}
		
		return msgdtoList;
	}
	


public int createChatMessage(ChatMessageDto dto) {
    int result = -1;
    try {
        // ChatMessage 엔티티 생성 및 저장
        ChatMessage msg = ChatMessage.builder()
                .roomNo(dto.getRoom_no())
                .messageContent(dto.getMessage_content())
                .messageDate(LocalDateTime.now())
                .empCode(dto.getEmp_code())
                .build();
        chatMessageRepository.save(msg);

        // 채팅방에 속한 사용자 리스트 조회
        List<ChatInvite> invites = chatInviteRepository.findByChatRoomRoomNo(dto.getRoom_no());

        // 조회된 사용자의 WebSocket 세션에 메시지 전송
        for (ChatInvite invite : invites) {
            Long empCode = invite.getEmployee().getEmpCode();
            sendMessageToUser(empCode, msg);  // WebSocket 세션이 있는 경우 메시지 전송
        }

        result = 1; // 성공 시 1 반환
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}

// 특정 사용자에게 메시지 전송
private void sendMessageToUser(Long empCode, ChatMessage message) {
    // WebSocket 세션에서 사용자 세션을 가져옴
	// empCode를 String으로 변환하여 세션을 가져옵니다.
	WebSocketSession session = sessionManager.getSession(empCode.toString());

    if (session != null && session.isOpen()) {
        try {
            session.sendMessage(new TextMessage(message.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}