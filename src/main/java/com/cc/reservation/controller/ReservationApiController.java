package com.cc.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.reservation.domain.ReservationDto;
import com.cc.reservation.service.ReservationService;

@Controller
public class ReservationApiController {

	private final ReservationService reservationService;
	
	@Autowired
	public ReservationApiController(ReservationService resertReservationService) {
		this.reservationService = resertReservationService;
	}
	
	@PostMapping("/reservationCreate")
	@ResponseBody
	public Map<String, Object> createSchedule(@RequestBody ReservationDto dto) {
	    Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "회의실등록중 오류가 발생했습니다.");
		
		if(reservationService.createReservation(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "회의실이 성공적으로 등록되었습니다.");
		}
		
		return resultMap;
	}
	
	@PostMapping("/reservationUpdate")
	@ResponseBody
	public Map<String, Object> updateReservation(@RequestBody ReservationDto dto) {
	    Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("res_code", "200");
		resultMap.put("res_msg", "회의실 예약 수정중 오류가 발생했습니다.");
		
		if(reservationService.updateReservationOne(dto) != null) {
			resultMap.put("res_code", "404");
			resultMap.put("res_msg", "회의실 예약이 성공적으로 수정되었습니다.");
		}
		
		return resultMap;
	}
	
	@PostMapping("/reservationDelete/{reservation_no}")
	@ResponseBody
	public Map<String, Object> deleteReservation(@PathVariable("reservation_no") Long reservation_no) {
		System.out.println(reservation_no);
	    Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "회의실 예약 삭제중 오류가 발생했습니다.");
		
		if(reservationService.deleteReservationOne(reservation_no) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "회의실 예약이 성공적으로 삭제되었습니다.");
		}
		
		return resultMap;
	}
}
