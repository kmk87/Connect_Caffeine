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

	// 1. 출퇴근 표기
	public AttendanceDto getTodayAttendance(Long empCode) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // 오늘의 출근 기록 조회
        Optional<Attendance> attendanceOptional = attendanceRepository.findTodayAttendanceByEmpCode(empCode, startOfDay, endOfDay);

        // 출퇴근 기록이 있으면 DTO로 변환, 없으면 빈 객체 반환
        return attendanceOptional
                .map(attendance -> AttendanceDto.builder()
                        .attn_start(attendance.getAttnStart())
                        .attn_end(attendance.getAttnEnd())
                        .build())
                .orElse(new AttendanceDto()); // 출퇴근 기록이 없을 때 빈 Dto 반환
    }
	
	
    // 2. 출퇴근 기록 저장 후 표기
	public AttendanceDto recordAttendance(Long empCode, String action) {
		Employee employee = employeeRepository.findByempCode(empCode);
		
		// 오늘 날짜 지정
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
		
		// 오늘의 출근 기록 확인
        Optional<Attendance> optionalAttendance = attendanceRepository.findTodayAttendanceByEmployee(employee, startOfDay, endOfDay);

        Attendance attendance;
        
        if (optionalAttendance.isPresent()) {
            // 이미 출근 기록이 있을 경우
            attendance = optionalAttendance.get();

            if ("end".equals(action)) {
                // 퇴근 시간 업데이트
                attendance = Attendance.builder()
                    .attnNo(attendance.getAttnNo())  // 기존 출퇴근 기록 유지
                    .attnStart(attendance.getAttnStart())
                    .attnEnd(LocalDateTime.now())    // 퇴근 시간 업데이트
                    .employees(employee)
                    .build();
            }
        } else {
            // 출근 기록이 없을 경우 새로 생성
            attendance = Attendance.builder()
                .attnStart(LocalDateTime.now())  // 출근 시간 설정
                .employees(employee)             // 직원 정보
                .build();
        }

	    // 출근/퇴근 기록 저장
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
