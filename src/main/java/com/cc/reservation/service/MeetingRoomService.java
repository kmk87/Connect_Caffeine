package com.cc.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.reservation.domain.Building;
import com.cc.reservation.domain.MeetingRoom;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.repository.BuildingRepository;
import com.cc.reservation.repository.MeetingRoomRepository;

@Service
public class MeetingRoomService {

	private final MeetingRoomRepository meetingRoomRepository;
	private final BuildingRepository buildingRepository;
	public MeetingRoomService(MeetingRoomRepository meetingRoomRepository, BuildingRepository buildingRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
		this.buildingRepository = buildingRepository;
	}
	
	public boolean deleteMeetingRoom (Long meetingNo) {
		try {
			meetingRoomRepository.deleteById(meetingNo);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
	
	
	public MeetingRoomDto updateMeetingRoom(Long meeting_no, MeetingRoomDto updatedDto) {
		MeetingRoom meetingroom = meetingRoomRepository.findBymeetingNo(meeting_no);
		  meetingroom.setMeetingName(updatedDto.getMeeting_name());
		    meetingroom.setMeetingLocation(updatedDto.getMeeting_location());
		    meetingroom.setMaxPeople(updatedDto.getMax_people());

		    // 업데이트된 정보를 데이터베이스에 저장
		    meetingRoomRepository.save(meetingroom);

		    // 업데이트된 DTO 반환
		    return MeetingRoomDto.builder()
		            .meeting_no(meetingroom.getMeetingNo())
		            .meeting_name(meetingroom.getMeetingName())
		            .meeting_location(meetingroom.getMeetingLocation())
		            .max_people(meetingroom.getMaxPeople())
		            .building_no(meetingroom.getBuilding().getBuildingNo())
		            .build();
	    }
	
	
	//회의실을 생성하는 메서드
	public MeetingRoom createMeetingRoom(MeetingRoomDto dto){
		System.out.println(dto.getBuilding_no());
		Building building = buildingRepository.findBybuildingNo(dto.getBuilding_no());
		MeetingRoom meetingroom = MeetingRoom.builder()
				.meetingName(dto.getMeeting_name())
				.meetingLocation(dto.getMeeting_location())
				.maxPeople(dto.getMax_people())
				.building(building)
				.build();
		 return meetingRoomRepository.save(meetingroom);
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
	
	//빌딩의 정보(PK)을 기준으로 해당 값을 가지고있는 회의실을 조회하여 화면뿌리는 메서드
	public List<MeetingRoomDto> selectMeetingRoomOne(Long building_no) {
	    // meetingRooms 리스트를 repository에서 가져옴
	    List<MeetingRoom> meetingRooms = meetingRoomRepository.findByBuilding_BuildingNo(building_no);

	    // MeetingRoomDto 객체들을 담을 리스트를 생성
	    List<MeetingRoomDto> dtos = new ArrayList<>();

	    // meetingRooms 리스트의 각 MeetingRoom 객체를 MeetingRoomDto로 변환하여 dtos 리스트에 추가
	    for (MeetingRoom meetingRoom : meetingRooms) {
	        MeetingRoomDto dto = MeetingRoomDto.builder()
	            .meeting_no(meetingRoom.getMeetingNo())
	            .meeting_name(meetingRoom.getMeetingName())
	            .meeting_location(meetingRoom.getMeetingLocation())
	            .building_no(meetingRoom.getBuilding().getBuildingNo()) // Building의 no 필드 사용
	            .max_people(meetingRoom.getMaxPeople()) // max_people 필드 사용
	            .build();
	        
	        // 변환된 dto 객체를 리스트에 추가
	        dtos.add(dto);
	    }

	    return dtos; // 변환된 dto 리스트 반환
}
}