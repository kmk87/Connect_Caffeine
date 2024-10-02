package com.cc.notification.service;



import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
            notificationDto.setRelatedLink("/calendar");
            
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
            notificationDto.setRelatedLink("/calendar");

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
            notificationDto.setRelatedLink("//calendar");
            
            notificationHandler.saveAndSendNotification(notificationDto);
        }
    }
    
    // 공지사항 알림 발송
    public void sendNoticeNotification(String message, Long noticeId) throws Exception {
    	List<Employee> allEmployees = employeeService.getAllEmployees();
    	for (Employee employee : allEmployees) {
    		NotificationDto notificationDto = new NotificationDto();
    		notificationDto.setReceiver_no(employee.getEmpCode());
    		notificationDto.setNotificationContent(message);
    		notificationDto.setNotificationType("NOTICE");
    		notificationDto.setIsRead('N');
    		notificationDto.setRelatedLink("noticeDetail/"+noticeId);
    		
    		notificationHandler.saveAndSendNotification(notificationDto);
    	}
    }
    
    // 상대 시간을 계산하는 메서드
    public String calculateRelativeTime(LocalDateTime sentTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(sentTime, now);
        
        if (minutes < 1) {
            return "방금 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        }
        
        long hours = ChronoUnit.HOURS.between(sentTime, now);
        if (hours < 24) {
            return hours + "시간 전";
        }
        
        long days = ChronoUnit.DAYS.between(sentTime, now);
        if (days < 7) {
            return days + "일 전";
        }
        
        return "오래 전";
    }
    
    //전체 알림 조회 
    public List<NotificationDto> getNotificationsForUser(Long empCode) {
        // 1. 사용자의 알림 목록을 데이터베이스에서 조회
        List<Notification> notifications = notificationRepository.findByEmployeeEmpCodeOrderBySentTimeDesc(empCode);
        System.out.println(empCode);
        // 2. NotificationDto 리스트 생성
        List<NotificationDto> notificationDtoList = new ArrayList<NotificationDto>();

        // 3. 각 Notification 객체를 NotificationDto로 변환하고 리스트에 추가
        for (Notification notification : notifications) {
        	NotificationDto dto = new NotificationDto().toDto(notification);
            
        	// 상대 시간 계산 후 DTO에 추가
            String relativeTime = calculateRelativeTime(notification.getSentTime());
            dto.setRelativeTime(relativeTime);
            
        	// 4. 리스트에 변환된 DTO 추가
            notificationDtoList.add(dto);
        }
       System.out.println("notificationDtoList : "+notificationDtoList);
        // 5. DTO 리스트 반환
        return notificationDtoList;
    }
    
    //안읽은 알림 조회
    public List<NotificationDto> getUnreadNotifications(Long empCode) {
        List<Notification> unreadNotifications = notificationRepository.findByEmployeeEmpCodeAndIsRead(empCode, 'N');
        List<NotificationDto> notificationDtoList = new ArrayList<NotificationDto>();

        // 3. 각 Notification 객체를 NotificationDto로 변환하고 리스트에 추가
        for (Notification notification : unreadNotifications) {
        	NotificationDto dto = new NotificationDto().toDto(notification);
            
        	// 상대 시간 계산 후 DTO에 추가
            String relativeTime = calculateRelativeTime(notification.getSentTime());
            dto.setRelativeTime(relativeTime);
            
        	// 4. 리스트에 변환된 DTO 추가
            notificationDtoList.add(dto);
        }
      
        // 5. DTO 리스트 반환
        return notificationDtoList;
    }
    
   // 알림 삭제
    public int deleteNotificationsByIds(List<Long> notificationIds) {
		int result = 0;
		try {
			notificationRepository.deleteAllById(notificationIds);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    //알림 읽음 처리
    public boolean markAsRead(List<Long> notificationIds) {
        try {
            notificationRepository.markAsRead(notificationIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 읽음 처리(종모양) 
    @Transactional
    public void updateNotificationReadStatus(Long notificationId) {
        notificationRepository.updateNotificationReadStatus(notificationId);
    }
    
 // 일정 시간이 지난 알림 자동 삭제 
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    public void deleteOldNotifications() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(10); 
        List<Notification> oldNotifications = notificationRepository.findBySentTimeBefore(thresholdDate);
        
        if (!oldNotifications.isEmpty()) {
            List<Long> oldNotificationIds = oldNotifications.stream()
                    .map(Notification::getNotificationNo)
                    .collect(Collectors.toList());
            notificationRepository.deleteAllById(oldNotificationIds);
            System.out.println(oldNotificationIds.size() + "개의 오래된 알림이 삭제되었습니다.");
        }
    }


    
}





  