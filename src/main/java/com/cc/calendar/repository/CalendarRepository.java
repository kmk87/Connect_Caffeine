package com.cc.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cc.calendar.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{
	
	Calendar findByScheduleNo(Long scheduleNo);
	
	// 일정 종류에 따라 일정을 조회하는 메서드 추가
    List<Calendar> findByScheduleTypeAndEmployee_EmpCode(Long scheduleType, Long empCode);
    

   
    List<Calendar> findAll();

}
