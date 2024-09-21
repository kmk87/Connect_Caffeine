package com.cc.calendar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.calendar.domain.Calendar;
import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.repository.CalendarRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class CalendarService {
	
	private final EmployeeRepository employeeRepository;
	private final CalendarRepository calendarRepository;
	
	@Autowired
	public CalendarService(EmployeeRepository employeeRepository, CalendarRepository calendarRepository) {
		this.employeeRepository = employeeRepository;
		this.calendarRepository = calendarRepository;
	}
	
	public Calendar createSchedule(CalendarDto dto){
		Long calendarWriter = dto.getCalendar_writer_no();
		Employee employee = employeeRepository.findByempCode(calendarWriter);
		
		
		if (employee == null) {
			throw new IllegalArgumentException("유효하지 않은 emp_code: " + calendarWriter);
		}
		
		Calendar calendar = Calendar.builder()
							.scheduleType(dto.getSchedule_type())
							.scheduleTitle(dto.getSchedule_title())
							.scheduleContent(dto.getSchedule_content())
							.location(dto.getLocation())
							.startTime(dto.getStart_time())
							.endTime(dto.getEnd_time())
							.employee(employee)
							.build();
		return calendarRepository.save(calendar);
	}
	
	public List<CalendarDto> selectCalendarList(Long empCode, Long deptNo, Long teamNo) {
//	    List<Calendar> calendarList = calendarRepository.findAllWithColors(); // 조인된 데이터를 가져옴
//	    List<CalendarDto> calendarDtoList = new ArrayList<>();
//
//	    for (Calendar calendar : calendarList) {
//	        CalendarDto calendarDto = new CalendarDto().toDto(calendar);
//	        calendarDto.setColor_code(calendar.getColor().getColorCode());  // 색상 코드 설정
//	        calendarDtoList.add(calendarDto);
//	    }
//	    return calendarDtoList;
		 // 모든 일정을 가져오되, 사용자의 empCode, deptNo, teamNo에 따라 필터링
		 List<Calendar> calendarList = calendarRepository.findAllWithColors();
		    List<CalendarDto> calendarDtoList = new ArrayList<>();

		    for (Calendar calendar : calendarList) {
		        boolean includeEvent = false;
		        
		        // 일정 타입에 따라 필터링
		        if (calendar.getScheduleType() == 1) { // 내일정
		            includeEvent = calendar.getEmployee().getEmpCode().equals(empCode); // 로그인한 사용자가 작성한 일정만 표시
		        } else if (calendar.getScheduleType() == 2) { // 전사일정
		            includeEvent = true; // 모든 사용자에게 보임
		        } else if (calendar.getScheduleType() == 3) { // 부서일정
		            includeEvent = calendar.getEmployee().getEmpGroup().getGroupParentNo().equals(deptNo); // 같은 부서일 경우에만 표시
		        } else if (calendar.getScheduleType() == 4) { // 팀일정
		            includeEvent = calendar.getEmployee().getEmpGroup().getGroupNo().equals(teamNo); // 같은 팀일 경우에만 표시
		        }

		        // 일정 추가 (includeEvent가 true일 때만)
		        if (includeEvent) {
		            CalendarDto calendarDto = new CalendarDto().toDto(calendar);
		            calendarDto.setColor_code(calendar.getColor().getColorCode()); // 색상 코드 설정
		            calendarDtoList.add(calendarDto);
		        }
		    }

		    return calendarDtoList;
	}

	
	public CalendarDto selectScheduleOne(Long schedule_no) {
		Calendar calendar = calendarRepository.findByScheduleNo(schedule_no);
		
		CalendarDto dto = CalendarDto.builder()
						.schedule_no(calendar.getScheduleNo())
						.schedule_title(calendar.getScheduleTitle())
						.schedule_type(calendar.getScheduleType())
						.schedule_content(calendar.getScheduleContent())
						.location(calendar.getLocation())
						.start_time(calendar.getStartTime())
						.end_time(calendar.getEndTime())
						.build();
		return dto;
	}
	
	@Transactional
	public Calendar updateSchedule(CalendarDto dto) {
	    // 기존 일정 엔티티를 조회
	    Calendar calendar = calendarRepository.findById(dto.getSchedule_no())
	            .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다: " + dto.getSchedule_no()));
	    
	    // 기존 엔티티에 새로운 값으로 업데이트
	    calendar.setScheduleTitle(dto.getSchedule_title());
	    calendar.setScheduleContent(dto.getSchedule_content());
	    calendar.setLocation(dto.getLocation());
	    calendar.setStartTime(dto.getStart_time());
	    calendar.setEndTime(dto.getEnd_time());

	    // 변경된 엔티티 저장
	    return calendarRepository.save(calendar);
	}
	
	public int deleteSchedule(Long schedule_no) {
		int result = 0;
		try {
			calendarRepository.deleteById(schedule_no);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	

		
		
}
