package com.cc.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.reservation.domain.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>{

}
