package com.cc.calendar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.calendar.domain.CalendarDto;
import com.cc.calendar.service.CalendarService;
import com.cc.employee.service.EmployeeService;
import com.cc.notification.service.NotificationService;

@Controller
public class CalendarApiController {
	
	private final CalendarService calendarService;
	private final NotificationService notificationService;
	private final EmployeeService employeeService;
	
	@Autowired
	public CalendarApiController(CalendarService calendarService,NotificationService notificationService,EmployeeService employeeService) {
		this.calendarService = calendarService;
		this.notificationService = notificationService;
		this.employeeService = employeeService;
	}
	
	
	@PostMapping("/calendar")
	@ResponseBody
	public Map<String, Object> createSchedule(@RequestBody CalendarDto dto) {
	    Map<String, Object> resultMap = new HashMap<>();
	    
	    try {
	        // 일정 등록 로직
	        if (calendarService.createSchedule(dto) != null) {
	            resultMap.put("schedule_no", dto.getSchedule_no());
	            resultMap.put("schedule_title", dto.getSchedule_title());
	            resultMap.put("start_time", dto.getStart_time());
	            resultMap.put("end_time", dto.getEnd_time());
	            resultMap.put("schedule_content", dto.getSchedule_content());
	            resultMap.put("location", dto.getLocation());
	            resultMap.put("res_code", "200");
	            resultMap.put("res_msg", "일정이 성공적으로 등록되었습니다.");
	            
	            // 알림 전송 로직
	            int scheduleType = dto.getSchedule_type();
	            Long writerId = dto.getCalendar_writer_no();  // 작성자의 ID
	            String message = "";
	            // 작성자의 팀번호 및 부서번호 가져오기
	            Long teamNo = employeeService.getGroupNoByEmpCode(writerId);
	            Long deptNo = employeeService.getDeptNoByTeamNo(teamNo);

	            System.out.println("writerId: " + writerId + ", deptNo: " + deptNo + ", teamNo: " + teamNo);
	            switch (scheduleType) {
	                case 2:  // 전사일정
	                	message += "[전사일정] " + dto.getSchedule_title() + "이(가) 등록되었습니다.";
	                    notificationService.sendCompanyWideNotification(message,writerId);
	                    break;
	                case 3:  // 부서일정
	                	message += "[부서일정] " + dto.getSchedule_title() + "이(가) 등록되었습니다.";
	                    notificationService.sendDepartmentNotification(deptNo, message,writerId);
	                    break;
	                case 4:  // 팀일정
	                	message += "[팀일정] " + dto.getSchedule_title() + "이(가) 등록되었습니다.";
	                    notificationService.sendTeamNotification(teamNo, message,writerId);
	                    break;
	                case 1:  // 내일정은 알림 없음
	                    break;
	                default:
	                    throw new IllegalArgumentException("Unknown schedule type: " + scheduleType);
	            }
	        } else {
	            resultMap.put("res_code", "404");
	            resultMap.put("res_msg", "일정 등록 중 오류가 발생하였습니다.");
	        }
	    } catch (Exception e) {
	        resultMap.put("res_code", "500");
	        resultMap.put("res_msg", "서버에서 일정 등록 중 오류가 발생했습니다.");
	        e.printStackTrace();  // 로그로 오류 기록
	    }
	    return resultMap;
	}

	
	@ResponseBody
	@PostMapping("/calendarEdit/{schedule_no}")
	public Map<String, String> updateSchedule(@RequestBody CalendarDto dto) { 
	    Map<String, String> resultMap = new HashMap<>();

	    resultMap.put("res_code", "404");
	    resultMap.put("res_msg", "일정 수정 중 오류가 발생했습니다.");
	    
	    if (calendarService.updateSchedule(dto) != null) {
	        resultMap.put("res_code", "200");
	        resultMap.put("res_msg", "정상적으로 일정이 수정되었습니다.");
	    }
	    return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/calendarDelete/{schedule_no}")
	public Map<String,String> deleteSchedule(@PathVariable("schedule_no") Long schedule_no){
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "일정 삭제중 오류가 발생했습니다.");
		
		
		if(calendarService.deleteSchedule(schedule_no) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "정상적으로 일정이 삭제되었습니다.");
			
		}
		return map;
	}
	
	@PostMapping("/calendar/update")
	public ResponseEntity<Map<String, String>> updateEvent(@RequestBody CalendarDto updatedEvent) {
	    try {
	        // 이벤트 업데이트 로직 (데이터베이스에 새로운 시간 저장)
	        calendarService.updateEvent(updatedEvent);

	        // 성공 메시지 반환
	        Map<String, String> response = new HashMap<>();
	        response.put("res_code", "200");
	        response.put("res_msg", "이벤트가 성공적으로 업데이트되었습니다.");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        // 실패 메시지 반환
	        Map<String, String> response = new HashMap<>();
	        response.put("res_code", "500");
	        response.put("res_msg", "업데이트 실패");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


}
