package com.cc.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.reservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{

}
