package com.cc.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.reservation.domain.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>{
    // Building 엔티티의 buildingNo 필드 참조
    List<MeetingRoom> findByBuilding_BuildingNo(Long buildingNo);
	
}
