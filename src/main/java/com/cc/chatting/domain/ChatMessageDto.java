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
    private String message_content;  // 메시지 내용
    private LocalDateTime message_date; // 메시지 날짜
    private String chat_type;        // 채팅 타입 (ex: 메시지 전송, 방 입장 등)
    private String sender_name;
    private String emp_name;

    // DTO에서 ChatMessage 엔티티로 변환
    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .messageNo(message_no) // 메시지 번호
                .roomNo(room_no) // 방 번호 설정
                .empCode(emp_code) // 사원 코드 설정
                .messageContent(message_content) // 메시지 내용 설정
                .messageDate(message_date != null ? message_date : LocalDateTime.now()) // 메시지 날짜 설정
                .build();
    }

    // ChatMessage 엔티티에서 DTO로 변환
    public static ChatMessageDto toDto(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .message_no(chatMessage.getMessageNo()) // 메시지 번호
                .emp_code(chatMessage.getEmpCode()) // 사원 코드
                .room_no(chatMessage.getRoomNo()) // 방 번호
                .message_content(chatMessage.getMessageContent()) // 메시지 내용
                .message_date(chatMessage.getMessageDate()) // 메시지 날짜
                .build();
    }
}
