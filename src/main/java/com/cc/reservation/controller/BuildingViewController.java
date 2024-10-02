package com.cc.reservation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cc.reservation.domain.BuildingDto;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.service.BuildingService;
import com.cc.reservation.service.MeetingRoomService;


@Controller
public class BuildingViewController {

	private final BuildingService buildingService;
	private final MeetingRoomService meetingRoomService;
	
	
	public BuildingViewController(BuildingService buildingService,
			MeetingRoomService meetingRoomService) {
		this.buildingService = buildingService;
		this.meetingRoomService = meetingRoomService;
	}
	
	
	@GetMapping("/Building")
	public String Building(Model model) {
		
		List<BuildingDto> building = buildingService.selectBuildingList();
		List<MeetingRoomDto> meeting = meetingRoomService.selectMeetingRoomList();
		
		model.addAttribute("buildings", building);
		model.addAttribute("meetings",meeting);
		
		return "/reservation/building_create";
	}
	
	@GetMapping("/building_information/{buildingNo}")
	public String BuildingInformation(@PathVariable("buildingNo") Long buildingNo,
			Model model) {
		List<BuildingDto> building = buildingService.selectBuildingList();
		List<MeetingRoomDto> meeting = meetingRoomService.selectMeetingRoomList();
		System.out.println(buildingNo);
		BuildingDto buildingDto = buildingService.selectBuildingOne(buildingNo);
		System.out.println(buildingDto);
		
		model.addAttribute("buildings", building);
		model.addAttribute("meetings",meeting);
		model.addAttribute("dto", buildingDto);
		return "/reservation/building_information";
	}
	
	
	@GetMapping("/building_meetingroom/{buildingNo}")
	public String MeetingRoomInfomation(@PathVariable("buildingNo") Long buildingNo, Model model) {
		List<BuildingDto> building = buildingService.selectBuildingList();
		List<MeetingRoomDto> meeting = meetingRoomService.selectMeetingRoomList();
		BuildingDto buildingDto = buildingService.selectBuildingOne(buildingNo);
		List<MeetingRoomDto> meetingDto = meetingRoomService.selectMeetingRoomOne(buildingNo);
		
		model.addAttribute("buildings", building);
		model.addAttribute("meetings",meeting);
		model.addAttribute("dto", buildingDto);
		model.addAttribute("meetingRooms", meetingDto);


		return "/reservation/building_meetingroom";
	}
}


