package com.cc.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.reservation.service.ReservationService;

@Controller
public class ReservationViewController {

	private final ReservationService reservationService;
	
	public ReservationViewController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	
	@GetMapping("/Reservation")
	public String Reservation(Model model) {
		return "/reservation/reservation";
	}
	
}
