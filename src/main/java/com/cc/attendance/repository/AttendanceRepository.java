package com.cc.attendance.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.attendance.domain.Attendance;
import com.cc.attendance.domain.AttendanceDto;
import com.cc.employee.domain.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{

	@Query("SELECT new com.cc.attendance.domain.AttendanceDto(a.attnNo, a.attnStart, a.attnEnd, a.worktime, a.attnStatus) " +
		       "FROM Attendance a WHERE a.employees.empCode = :empCode ORDER BY a.attnStart DESC")
	List<AttendanceDto> findAttendancesByEmpCode(@Param("empCode") Long empCode);
	
//	// 오늘의 출퇴근 기록 가져오기
//	@Query("SELECT a FROM Attendance a WHERE a.employees.empCode = :empCode AND a.attnStart = :today")
	Attendance findByEmployees_EmpCodeAndAttnStart(Long empCode, LocalDateTime attnStart);

	Optional<Attendance> findByEmployeesAndAttnStart(Employee employee, LocalDate localDate);

	Optional<Attendance> findByEmployees(Employee employee);
}
