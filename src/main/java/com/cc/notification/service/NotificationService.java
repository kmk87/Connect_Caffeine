package com.cc.notification.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.employee.domain.Employee;
import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.NotificationDto;
import com.cc.websocket.notification.NotificationHandler;

@Service
@Transactional
public class NotificationService {


    private final NotificationHandler notificationHandler;
    private final EmployeeService employeeService;
	
	@Autowired
	public NotificationService (EmployeeService employeeService, NotificationHandler notificationHandler) {
		this.employeeService = employeeService;
		this.notificationHandler = notificationHandler;
	}

    // 전사일정 알림 전송
    public void sendCompanyWideNotification(String message) throws Exception {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        for (Employee employee : allEmployees) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setReceiver_no(employee.getEmpCode());
            notificationDto.setNotificationContent(message);
            notificationDto.setNotificationType("SCHEDULE");
            notificationDto.setIsRead('N');
            notificationDto.setRelatedLink("/calnedar/schedule");
            
            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());

            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }

    // 부서일정 알림 전송
    public void sendDepartmentNotification(Long deptNo, String message) throws Exception {
        List<Employee> departmentEmployees = employeeService.getEmployeesByDeptNo(deptNo);
        for (Employee employee : departmentEmployees) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setReceiver_no(employee.getEmpCode());
            notificationDto.setNotificationContent(message);
            notificationDto.setNotificationType("SCHEDULE");
            notificationDto.setIsRead('N');
            notificationDto.setRelatedLink("/calnedar/schedule");
            
            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());
            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }

    // 팀일정 알림 전송
    public void sendTeamNotification(Long teamNo, String message) throws Exception {
        List<Employee> teamEmployees = employeeService.getEmployeesByTeamNo(teamNo);
        for (Employee employee : teamEmployees) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setReceiver_no(employee.getEmpCode());
            notificationDto.setNotificationContent(message);
            notificationDto.setNotificationType("SCHEDULE");
            notificationDto.setIsRead('N');
            notificationDto.setRelatedLink("/calnedar/schedule");
            
            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());
            
            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }
}





  

