package com.cc.notification.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.notification.service.NotificationService;

@Controller
public class NotificationApiController {
	
	private final NotificationService notificationService;
	
	@Autowired
	public NotificationApiController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@ResponseBody
	@DeleteMapping("/notificationDelete")
	public Map<String, String> deleteNotifications(@RequestBody Map<String, List<Long>> requestData) {
		Map<String, String> response = new HashMap<>();
	    response.put("res_code", "404");
	    response.put("res_msg", "알림 삭제 중 오류가 발생했습니다.");

	    List<Long> notificationIds = requestData.get("notificationIds");
	    try {
	        if (notificationService.deleteNotificationsByIds(notificationIds) > 0) {
	            response.put("res_code", "200");
	            response.put("res_msg", "정상적으로 알림이 삭제되었습니다.");
	        }
	    } catch (Exception e) {
	        // 예외 처리 (필요 시 로깅)  
	        e.printStackTrace();
	    }

	    return response;
	}
	
	@PutMapping("/notificationRead")
	@ResponseBody
	public Map<String, String> markNotificationsAsRead(@RequestBody Map<String, List<Long>> requestData) {
	    List<Long> notificationIds = requestData.get("notificationIds");
	    Map<String, String> response = new HashMap<>();
	    System.out.println("notificationIds : "+notificationIds);
	    if (notificationService.markAsRead(notificationIds)) {
	        response.put("res_code", "200");
	        response.put("res_msg", "알림이 읽음 처리되었습니다.");
	    } else {
	        response.put("res_code", "500");
	        response.put("res_msg", "알림 읽음 처리에 실패했습니다.");
	    }
	    
	    return response;
	}
	



}
