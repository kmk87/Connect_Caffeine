package com.cc.reservation.domain;

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
@Table(name="meeting_room")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class MeetingRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="meeting_no")
	private Long meetingNo;
	
	@Column(name="meeting_name")
	private String meetingName;
	
	@Column(name="meeting_location")
	private String meetingLocation;
	
	@Column(name="max_people")
	private Long maxPeople;

	@ManyToOne()
	@JoinColumn(name = "building_no")
	private Building building;
	
	
	
}
