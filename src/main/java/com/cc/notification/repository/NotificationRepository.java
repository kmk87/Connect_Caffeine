package com.cc.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.notification.domain.Notification;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
	// 수신자의 empCode를 통해 알림 목록을 조회하는 메서드
    List<Notification> findByEmployeeEmpCodeOrderBySentTimeDesc(Long empCode);
    
    // 수신자(employee)와 읽음 여부(isRead)를 기준으로 알림을 조회하는 메서드
    List<Notification> findByEmployeeEmpCodeAndIsRead(Long empCode, char isRead); 
    
    // 알림페이지에서 읽음 처리
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = 'Y' WHERE n.notificationNo IN :notificationIds")
    void markAsRead(@Param("notificationIds") List<Long> notificationIds);
    
    //헤더 종모양에서 읽음 처리 
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = 'Y' WHERE n.id = :notificationId")
    void updateNotificationReadStatus(@Param("notificationId") Long notificationId);
    
 // 오래된 알림 찾기 (예: 30일 이상 지난 알림 조회)
    List<Notification> findBySentTimeBefore(LocalDateTime dateTime);  // 추가된 메서드

}
