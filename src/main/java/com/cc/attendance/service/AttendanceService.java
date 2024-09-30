package com.cc.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	private final AttendanceRepository attendanceRepository;
	
	public AttendanceService(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

	// 1. 근태 리스트
	public List<AttendanceDto> getAttendancesByEmpCode(Long emp_code) {
        
		List<AttendanceDto> attnList = attendanceRepository.findAttendancesByEmpCode(emp_code);
        
        return attnList;
    }
}