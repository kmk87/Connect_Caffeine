package com.cc.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.attendance.domain.Attendance;
import com.cc.attendance.domain.AttendanceDto;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{

	@Query("SELECT new com.cc.attendance.domain.AttendanceDto(a.attnNo, a.attnStart, a.attnEnd, a.worktime, a.attnStatus) " +
		       "FROM Attendance a WHERE a.employees.empCode = :empCode ORDER BY a.attnStart DESC")
	List<AttendanceDto> findAttendancesByEmpCode(@Param("empCode") Long empCode);
}