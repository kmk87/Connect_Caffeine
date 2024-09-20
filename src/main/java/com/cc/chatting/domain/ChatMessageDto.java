package com.cc.chatting.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatMessageDto {
    private Long message_no;         // 메시지 번호
    private Long emp_code;           // 사원 코드
    private Long room_no;            // 방 번호
    private String message_content;   // 메시지 내용
    private LocalDateTime message_date; // 메시지 날짜

    // ChatInvite 정보를 사용하여 ChatMessage 엔티티로 변환
    public ChatMessage toEntity(ChatInvite chatInvite) {
        return ChatMessage.builder()
                .messageNo(message_no) // 메시지 번호
                .chatInvite(chatInvite) // ChatInvite 설정
                .messageContent(message_content) // 메시지 내용
                .messageDate(message_date) // 메시지 날짜
                .build();
    }

    // ChatMessage 엔티티를 DTO로 변환
    public static ChatMessageDto toDto(ChatMessage chatMessage) {
        ChatInvite chatInvite = chatMessage.getChatInvite();
        return ChatMessageDto.builder()
                .message_no(chatMessage.getMessageNo()) // 메시지 번호
                .emp_code(chatInvite != null && chatInvite.getEmployee() != null ? 
                         chatInvite.getEmployee().getEmpCode() : null) // ChatInvite에서 사원 코드 추출
                .room_no(chatInvite != null && chatInvite.getChatRoom() != null ? 
                         chatInvite.getChatRoom().getRoomNo() : null) // ChatInvite에서 방 번호 추출
                .message_content(chatMessage.getMessageContent()) // 메시지 내용
                .message_date(chatMessage.getMessageDate()) // 메시지 날짜
                .build();
    }
}
