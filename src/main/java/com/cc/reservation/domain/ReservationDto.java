package com.cc.reservation.domain;

import java.time.LocalDateTime;

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
public class ReservationDto {
	private Long reservation_no;
	private Long meeting_no;
	private LocalDateTime meeting_start;
	private LocalDateTime meeting_end;
	private Long meeting_people;
	private String meeting_content;
	
	public Reservation toEnetity(MeetingRoom meetingroom) {
		return Reservation.builder()
				.reservationNo(reservation_no)
				.meetingroom(meetingroom)
				.meetingStart(meeting_start)
				.meetingEnd(meeting_end)
				.meetingPeople(meeting_people)
				.meetingContent(meeting_content)
				.build();
	}
	
	public ReservationDto toDto(Reservation reservation) {
		return ReservationDto.builder()
				.reservation_no(reservation.getReservationNo())
				.meeting_no(reservation.getMeetingroom().getMeetingNo())
				.meeting_start(reservation.getMeetingStart())
				.meeting_end(reservation.getMeetingEnd())
				.meeting_people(reservation.getMeetingPeople())
				.meeting_content(reservation.getMeetingContent())
				.build();
	}
}