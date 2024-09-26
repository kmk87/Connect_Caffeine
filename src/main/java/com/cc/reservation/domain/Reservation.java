package com.cc.reservation.domain;

import java.time.LocalDateTime;

import com.cc.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reservation")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reservation_no")
	private Long reservationNo;
	
	@ManyToOne()
	@JoinColumn(name = "emp_code")
	private Employee employee;
	
	@ManyToOne()
	@JoinColumn(name = "meeting_no")
	private MeetingRoom meetingroom;
	
	@Column(name="meeting_start")
	private LocalDateTime meetingStart; 
	
	@Column(name="meeting_end")
	private LocalDateTime meetingEnd;
	
	@Column(name="meeting_people")
	private Long meetingPeople;
	
	@Column(name="meeting_content")
	private String meetingContent;
	
	
}
