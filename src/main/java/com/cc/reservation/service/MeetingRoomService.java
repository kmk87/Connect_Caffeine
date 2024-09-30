package com.cc.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.reservation.domain.MeetingRoom;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.repository.BuildingRepository;
import com.cc.reservation.repository.MeetingRoomRepository;

@Service
public class MeetingRoomService {

	private final MeetingRoomRepository meetingRoomRepository;
	
	public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
	}
	
	//회의실은 빌딩안에 있음 회의실 정보에 건물 번호가 존재 건물의 번호에 존재하는 회의실만을 갖고오는 메소드
	public List<MeetingRoomDto> selectMeetingRoomList(){
		List<MeetingRoom> meetingRoomList = null;
		meetingRoomList = meetingRoomRepository.findAll();
		List<MeetingRoomDto> meetingRoomDtoList = new ArrayList<MeetingRoomDto>();
				for(MeetingRoom m : meetingRoomList) {
					MeetingRoomDto dto = new MeetingRoomDto().toDto(m);
					meetingRoomDtoList.add(dto);
				}
		return meetingRoomDtoList;
	}
	//빌딩의 정보를 들고와야되는데 회의실로 착각함 일단 필요할지도 모르니 나둠
//	public MeetingRoomDto selectMeetingRoomOne(Long building_no) {
//		MeetingRoom meetingRoom = meetingRoomRepository.findByBuildingNo(building_no);
//		MeetingRoomDto dto = MeetingRoomDto.builder()
//				.meeting_no(meetingRoom.getMeetingNo())
//				.meeting_name(meetingRoom.getMeetingName())
//				.meeting_location(meetingRoom.getMeetingLocation())
//				.building_no(meetingRoom.getBuilding())
//				.build();
//	}
}
