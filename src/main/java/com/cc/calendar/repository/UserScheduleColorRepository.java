package com.cc.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cc.calendar.domain.UserScheduleColor;
import com.cc.employee.domain.Employee;

@Repository
public interface UserScheduleColorRepository extends JpaRepository<UserScheduleColor, Long> {
    
	

	// Employee와 scheduleType을 이용하여 UserScheduleColor 조회
    UserScheduleColor findByEmployeeAndScheduleType(Employee employee, Long scheduleType);
    
    // 수정된 쿼리 메서드: employee 엔티티의 empCode 필드를 사용해야 함
    UserScheduleColor findByEmployee_EmpCodeAndScheduleType(Long empCode, Long scheduleType);
    
 

}
