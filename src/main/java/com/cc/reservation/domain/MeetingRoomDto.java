package com.cc.reservation.domain;

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
public class MeetingRoomDto {

	private Long meeting_no;
	private String meeting_name;
	private String meeting_location;
	private Long max_people;
	private Long building_no;
	
	public MeetingRoom toEntity(Building building) {
		return MeetingRoom.builder()
				.meetingNo(meeting_no)
				.meetingName(meeting_name)
				.maxPeople(max_people)
				.building(building)
				.meetingLocation(meeting_location)
				.build();
	}
	
	public MeetingRoomDto toDto(MeetingRoom meetingRoom) {
		return MeetingRoomDto.builder()
				.meeting_no(meetingRoom.getMeetingNo())
				.meeting_name(meetingRoom.getMeetingName())
				.meeting_location(meetingRoom.getMeetingLocation())
				.max_people(meetingRoom.getMaxPeople())
				.building_no(meetingRoom.getBuilding().getBuildingNo())
				.build();
	}
}
