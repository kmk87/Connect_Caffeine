package com.cc.chatting.domain;

import com.cc.employee.domain.Employee;

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
public class ChatInviteDto {
	private Long invite_no;
	private Long emp_code;
	private Long room_no;
	
	public ChatInvite toEntity(Employee employee,
			ChatRoom chatRoom) {
		return ChatInvite.builder()
				.inviteNo(invite_no)
				.chatRoom(chatRoom)
				.employee(employee)
				.build();
	}
	public ChatInviteDto toDto(ChatInvite chatInvite) {
		return ChatInviteDto.builder()
				.invite_no(chatInvite.getInviteNo())
				.room_no(chatInvite.getChatRoom().getRoomNo())
				.emp_code(chatInvite.getEmployee().getEmpCode())
				.build();
	}
}
