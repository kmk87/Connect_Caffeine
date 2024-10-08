package com.cc.chatting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.cc.chatting.domain.ChatRoomVo;

@Mapper
public interface ChatRoomMapper {
	
	List<ChatRoomVo> selectChatRoomList(@Param("roomNoList") List<Long> roomNoList);
}