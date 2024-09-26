package com.cc.reservation.service;

import org.springframework.stereotype.Service;

import com.cc.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	
}
