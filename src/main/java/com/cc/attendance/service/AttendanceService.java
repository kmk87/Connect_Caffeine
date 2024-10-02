package com.cc.attendance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.attendance.domain.Attendance;
import com.cc.attendance.domain.AttendanceDto;
import com.cc.attendance.repository.AttendanceRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class AttendanceService {
	
	private final EmployeeRepository employeeRepository;
	private final AttendanceRepository attendanceRepository;
	
	public AttendanceService(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository) {
		this.employeeRepository = employeeRepository;
		this.attendanceRepository = attendanceRepository;
	}

    
	public AttendanceDto recordAttendance(Long empCode, String action) {
		Employee employee = employeeRepository.findByempCode(empCode);

		// Optional 처리
		Attendance attendance = attendanceRepository.findByEmployees(employee)
		        .orElseGet(() -> createAttendanceRecord(employee));

        if ("start".equals(action)) {
            attendance = attendance.builder().attnStart(LocalDateTime.now()).build();
        } else if ("end".equals(action)) {
            attendance = attendance.builder().attnEnd(LocalDateTime.now()).build();
        }

        attendanceRepository.save(attendance);
        return AttendanceDto.builder()
                .attn_start(attendance.getAttnStart())
                .attn_end(attendance.getAttnEnd())
                .build();
    }

    private Attendance createAttendanceRecord(Employee employee) {
        return Attendance.builder()
                .employees(employee)
                .build();
    }
	
	
	
	
	// 3. 근태 리스트
	public List<AttendanceDto> getAttendancesByEmpCode(Long emp_code) {
        
		List<AttendanceDto> attnList = attendanceRepository.findAttendancesByEmpCode(emp_code);
        
        return attnList;
    }
}
