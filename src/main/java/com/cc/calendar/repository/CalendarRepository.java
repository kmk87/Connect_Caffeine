package com.cc.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.calendar.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long>{
	

}
