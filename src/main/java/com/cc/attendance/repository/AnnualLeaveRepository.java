package com.cc.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.attendance.domain.AnnualLeave;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave,Long>{

	
	@Query("SELECT MONTH(a.annualLeaveStart) AS month, SUM(a.annualLeaveUseCount) AS used_count " +
		       "FROM AnnualLeave a WHERE a.employees.empCode = :empCode GROUP BY MONTH(a.annualLeaveStart)")
		List<Object[]> findMonthlyLeaveUsage(@Param("empCode") Long empCode);
		
		
//		// 특정 사원의 연차 기록을 모두 조회
//	    List<AnnualLeave> findByEmpCode(@Param("empCode") Long empCode);
}
