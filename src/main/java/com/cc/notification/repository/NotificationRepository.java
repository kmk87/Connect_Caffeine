package com.cc.notification.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.notification.domain.Notification;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
	// 수신자의 empCode를 통해 알림 목록을 조회하는 메서드
    List<Notification> findByEmployeeEmpCode(Long empCode);
}
