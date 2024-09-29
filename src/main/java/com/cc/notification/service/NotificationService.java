package com.cc.notification.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.employee.domain.Employee;
import com.cc.employee.service.EmployeeService;
import com.cc.notification.domain.Notification;
import com.cc.notification.domain.NotificationDto;
import com.cc.notification.repository.NotificationRepository;
import com.cc.websocket.notification.NotificationHandler;

@Service
@Transactional
public class NotificationService {


    private final NotificationHandler notificationHandler;
    private final EmployeeService employeeService;
    private final NotificationRepository notificationRepository;
	
	@Autowired
	public NotificationService (EmployeeService employeeService, NotificationHandler notificationHandler,NotificationRepository notificationRepository) {
		this.employeeService = employeeService;
		this.notificationHandler = notificationHandler;
		this.notificationRepository = notificationRepository;
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
            
//            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
//            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
//            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());

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
            
//            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
//            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
//            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());
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
            
//            System.out.println("NotificationDto Receiver: " + notificationDto.getReceiver_no());
//            System.out.println("NotificationDto Message: " + notificationDto.getNotificationContent());
//            System.out.println("NotificationDto Type: " + notificationDto.getNotificationType());
            
            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }
    
    public List<NotificationDto> getNotificationsForUser(Long empCode) {
        // 1. 사용자의 알림 목록을 데이터베이스에서 조회
        List<Notification> notifications = notificationRepository.findByEmployeeEmpCode(empCode);
        System.out.println(empCode);
        // 2. NotificationDto 리스트 생성
        List<NotificationDto> notificationDtoList = new ArrayList<NotificationDto>();

        // 3. 각 Notification 객체를 NotificationDto로 변환하고 리스트에 추가
        for (Notification notification : notifications) {
        	NotificationDto dto = new NotificationDto().toDto(notification);
            // 4. 리스트에 변환된 DTO 추가
            notificationDtoList.add(dto);
        }
       System.out.println("notificationDtoList : "+notificationDtoList);
        // 5. DTO 리스트 반환
        return notificationDtoList;
    }
    
    public void sendNoticeNotification(String message) throws Exception {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        for (Employee employee : allEmployees) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setReceiver_no(employee.getEmpCode());
            notificationDto.setNotificationContent(message);
            notificationDto.setNotificationType("NOTICE");
            notificationDto.setIsRead('N');
            notificationDto.setRelatedLink("notice/list");
            

            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }

    
}





  

