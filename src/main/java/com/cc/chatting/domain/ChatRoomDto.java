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
public class ChatRoomDto {
	private Long room_no;
	private String last_msg;
	private LocalDateTime last_date;
	private String room_status;
	private String room_name;
	private String room_type;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.roomNo(room_no)
				.lastMsg(last_msg)
				.lastDate(last_date)
				.roomStatus(room_status)
				.roomName(room_name)
				.roomType(room_type)
				.build();
	}
	
	public ChatRoomDto toDto(ChatRoom chatRoom) {
		return ChatRoomDto.builder()
				.room_no(chatRoom.getRoomNo())
				.last_msg(chatRoom.getLastMsg())
				.last_date(chatRoom.getLastDate())
				.room_status(chatRoom.getRoomStatus())
				.room_name(chatRoom.getRoomName())
				.room_type(chatRoom.getRoomType())
				.build();
	}
}
