package com.cc.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.calendar.service.CalendarService;
import com.cc.calendar.service.ColorService;
import com.cc.calendar.service.UserScheduleColorService;
import com.cc.empGroup.service.EmpGroupService;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;
import com.cc.reservation.domain.BuildingDto;
import com.cc.reservation.domain.MeetingRoomDto;
import com.cc.reservation.domain.ReservationDto;
import com.cc.reservation.service.BuildingService;
import com.cc.reservation.service.MeetingRoomService;
import com.cc.reservation.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReservationViewController {
	private final BuildingService buildingService;
	private final MeetingRoomService meetingRoomService;
	private final CalendarService calendarService;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	private final ColorService colorService;
	private final UserScheduleColorService userScheduleColorService;
	private final EmpGroupService empGroupService;
	private final ReservationService reservationService;
	
	public ReservationViewController(CalendarService calendarService, EmployeeService employeeService, 
			EmployeeRepository employeeRepository,ColorService colorService,UserScheduleColorService userScheduleColorService,
			EmpGroupService empGroupService, BuildingService buildingService,
			MeetingRoomService meetingRoomService, ReservationService reservationService) {
		this.calendarService = calendarService;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
		this.colorService = colorService;
		this.userScheduleColorService = userScheduleColorService;
		this.empGroupService = empGroupService;
		this.buildingService = buildingService;
		this.meetingRoomService = meetingRoomService;
		this.reservationService = reservationService;
		}
	
	
	@GetMapping("/reservation")
	public String Reservation(HttpServletRequest request,Model model) {

			 
			List<BuildingDto> building = buildingService.selectBuildingList();
			List<MeetingRoomDto> meeting = meetingRoomService.selectMeetingRoomList();
			String currentUri = request.getRequestURI();
			model.addAttribute("currentUri", currentUri);

			model.addAttribute("buildings", building);
			model.addAttribute("meetings",meeting);
			
			List<ReservationDto> reservation = reservationService.getAllReservations();		  
			model.addAttribute("reservations",reservation);
			return "/reservation/reservation";
	}
	
	@GetMapping("/reservation/{reservation_no}")
	public ResponseEntity<ReservationDto> getReservationDetails(@PathVariable("reservation_no") Long reservationNo ){
		ReservationDto reservation = reservationService.selectReservationOne(reservationNo);
		return ResponseEntity.ok(reservation);
	}

}