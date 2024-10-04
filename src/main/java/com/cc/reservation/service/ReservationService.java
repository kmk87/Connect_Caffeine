package com.cc.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.reservation.domain.MeetingRoom;
import com.cc.reservation.domain.Reservation;
import com.cc.reservation.domain.ReservationDto;
import com.cc.reservation.repository.MeetingRoomRepository;
import com.cc.reservation.repository.ReservationRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
	
	private final EmployeeRepository employeeRepository;
	private final ReservationRepository reservationRepository;
	private final MeetingRoomRepository meetingRoomRepository;
	@Autowired
	public ReservationService(ReservationRepository reservationRepository,
			EmployeeRepository employeeRepository,
			MeetingRoomRepository meetingRoomRepository) {
		this.reservationRepository = reservationRepository;
		this.employeeRepository = employeeRepository;
		this.meetingRoomRepository = meetingRoomRepository;
	}
	
	public ReservationDto deleteReservationOne(Long reservation_no) {
		Reservation reservation = reservationRepository.findByReservationNo(reservation_no);
	    reservationRepository.delete(reservation);

	    // 삭제된 예약 정보를 DTO로 변환하여 반환 (필요에 따라 반환 생략 가능)
	    return ReservationDto.builder()
	            .meeting_no(reservation.getReservationNo())
	            .meeting_start(reservation.getMeetingStart())
	            .meeting_end(reservation.getMeetingEnd())
	            .meeting_content(reservation.getMeetingContent())
	            .meeting_people(reservation.getMeetingPeople())
	            .build();
	}
	
	
	public ReservationDto updateReservation(Long reservation_no) {
		Reservation reservation = reservationRepository.findByReservationNo(reservation_no);
		ReservationDto dto = ReservationDto.builder()
				.meeting_start(reservation.getMeetingStart())
				.meeting_no(reservation.getMeetingroom().getMeetingNo())
				.meeting_end(reservation.getMeetingEnd())
				.meeting_content(reservation.getMeetingContent())
				.meeting_people(reservation.getMeetingPeople())
				.build();
				return dto;
	}
	
	
	
	public Reservation updateReservationOne(ReservationDto dto) {
		
		Reservation reservation = reservationRepository.findByReservationNo(dto.getReservation_no());
	    // 기존 reservation 객체의 필드를 DTO 값으로 업데이트
	    reservation.setMeetingStart(dto.getMeeting_start());
	    reservation.setMeetingEnd(dto.getMeeting_end());
	    reservation.setMeetingContent(dto.getMeeting_content());
	    reservation.setMeetingPeople(dto.getMeeting_people());
		
		
		return reservationRepository.save(reservation);
	}
	
	public ReservationDto selectReservationOne(Long reservation_no) {
		Reservation reservation = reservationRepository.findByReservationNo(reservation_no);
		ReservationDto dto = ReservationDto.builder()
				.reservation_no(reservation.getReservationNo())
				.meeting_start(reservation.getMeetingStart())
				.meeting_no(reservation.getReservationNo())
				.meeting_end(reservation.getMeetingEnd())
				.meeting_content(reservation.getMeetingContent())
				.meeting_people(reservation.getMeetingPeople())
				.build();
				return dto;
	}
	
	
	
	public Reservation createReservation(ReservationDto dto) {
		MeetingRoom meetingroom = meetingRoomRepository.findBymeetingNo(dto.getMeeting_no());
		Reservation reservation = Reservation.builder()
				.meetingStart(dto.getMeeting_start())
				.meetingEnd(dto.getMeeting_end())
				.meetingPeople(dto.getMeeting_people())
				.meetingContent(dto.getMeeting_content())
				.meetingroom(meetingroom)
				.build();
		
				return reservationRepository.save(reservation);				
	}
	
	public List<ReservationDto> getAllReservations(){
		List<Reservation> reservationList = null;
		
		reservationList = reservationRepository.findAll();
		
		List<ReservationDto> reservationDtoList = new ArrayList<ReservationDto>();
		for(Reservation r: reservationList) {
			ReservationDto dto = new ReservationDto().toDto(r);
			reservationDtoList.add(dto);
		}
		return reservationDtoList;
		
	}
}
