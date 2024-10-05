package com.cc.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.reservation.domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Long>{

	Building findBybuildingNo(Long buildingNo);

}