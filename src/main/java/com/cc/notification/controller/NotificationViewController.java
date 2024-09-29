package com.cc.notification.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	    // 알림 가져오기
	    List<NotificationDto> notifications = notificationService.getNotificationsForUser(empCode);
	    model.addAttribute("notifications", notifications);
	    System.out.println("notifications : " + notifications);
	    return "notification/notification";
	}

	
//	@GetMapping("/header")
//	public String getHeaderNotifications(Model model) {
//	    // 로그인된 사용자 정보 가져오기
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    User user = (User) authentication.getPrincipal();
//	    String empAccount = user.getUsername();
//	    Long empCode = employeeService.findEmpCodeByEmpName(empAccount);
//	    
//	    // 사용자의 알림 목록 가져오기
//	    List<NotificationDto> notifications = notificationService.getNotificationsForUser(empCode);
//	    model.addAttribute("notifications", notifications);
//
//	    return "include/header"; // 알림이 있는 헤더 템플릿
//	}


}

