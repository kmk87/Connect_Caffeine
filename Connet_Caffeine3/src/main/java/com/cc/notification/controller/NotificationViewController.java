package com.cc.notification.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.NotificationDto;
import com.cc.notification.service.NotificationService;

@Controller
public class NotificationViewController {
	
	private final NotificationService notificationService;
	private final EmployeeService employeeService;

	@Autowired
	public NotificationViewController (NotificationService notificationService,EmployeeService employeeService) {
		this.notificationService = notificationService;
		this.employeeService = employeeService;
	}
	

	@GetMapping("/notification")
	public String getNotifications(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = (User) authentication.getPrincipal();
	    String empAccount = user.getUsername();
	    System.out.println("empAccount : " + empAccount);

	    // empCode 찾기
	    Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
	    
	    if (empCode == null) {
	        // empCode가 null이면 에러 메시지 처리하거나 빈 알림 리스트 반환
	        System.out.println("empCode가 null입니다. 알림을 가져올 수 없습니다.");
	        model.addAttribute("notifications", Collections.emptyList());
	        return "notification/notification";
	    }

	    // 전체 알림 가져오기
	    List<NotificationDto> notifications = notificationService.getNotificationsForUser(empCode);
	    model.addAttribute("notifications", notifications);
	    System.out.println("notifications : " + notifications);
	    
	    return "notification/notification";
	}
	
	// 안읽은 알림 가져오기
	 @GetMapping("/unreadNotification")
	    public String showUnreadNotifications(Model model) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    User user = (User) authentication.getPrincipal();
		    String empAccount = user.getUsername();
		    System.out.println("empAccount : " + empAccount);

		    // empCode 찾기
		    Long empCode = employeeService.findEmpCodeByEmpName(empAccount);

	        // 수신자가 현재 로그인한 사용자이고, 읽지 않은 알림 가져오기
	        List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(empCode);
	        model.addAttribute("unreadNotifications", unreadNotifications);
	        System.out.println("unreadNotifications : " + unreadNotifications);
	        return "notification/unReadNotification"; 
	    }

	 @PostMapping("/updateReadStatus")
	    public ResponseEntity<String> updateReadStatus(@RequestParam("notificationId") Long notificationId) {
	        notificationService.updateNotificationReadStatus(notificationId);
	        return ResponseEntity.ok("알림이 읽음 처리되었습니다.");
	    }



}

