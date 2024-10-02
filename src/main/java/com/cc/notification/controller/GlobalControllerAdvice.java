package com.cc.notification.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.NotificationDto;
import com.cc.notification.service.NotificationService;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    public GlobalControllerAdvice(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    @ModelAttribute
    public void addUnreadNotificationsToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String empAccount = user.getUsername();
            Long empCode = employeeService.findEmpCodeByEmpName(empAccount);

            // 읽지 않은 알림을 가져와서 모델에 추가
            List<NotificationDto> unreadNotifications = notificationService.getUnreadNotifications(empCode);
            model.addAttribute("unreadNotifications", unreadNotifications);
        }
    }
    
    
}
