package com.cc.notification.domain;

import java.time.LocalDateTime;



import com.cc.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="notification")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_no")
    private Long notificationNo;

    @ManyToOne
    @JoinColumn(name="receiver_id") 
    private Employee employee;  // 알림 수신자    

    @Column(name="sent_time")
    private LocalDateTime sentTime;

    @Column(name="notification_content")
    private String notificationContent;

    @Column(name="notification_type")
    private String notificationType;

    @Column(name="is_read", columnDefinition = "char(1) default 'N'")
    private char isRead = 'N';  // 기본값은 읽지 않은 상태 'N'

    @Column(name="related_link")
    private String relatedLink;
}
