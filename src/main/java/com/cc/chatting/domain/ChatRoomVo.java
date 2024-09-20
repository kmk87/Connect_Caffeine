package com.cc.chatting.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomVo {
	private int room_no;
	private String last_msg;
	private LocalDateTime last_date;
	private String room_status;
	private String room_name;
	private String room_type;
}
