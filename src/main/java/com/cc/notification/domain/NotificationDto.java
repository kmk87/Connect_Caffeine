package com.cc.notification.domain;

import java.time.LocalDateTime;

import com.cc.employee.domain.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NotificationDto {
	
	private Long notification_no;
	private Long receiver_no;
	private LocalDateTime sent_time;
	private String notificationContent;
	private String notificationType;
	private char isRead;
	private String relatedLink;
	private String relativeTime;
	
	public Notification toEntity(Employee employee) {
		return Notification.builder()
				.notificationNo(notification_no)
				.sentTime(sent_time)
				.notificationContent(notificationContent)
				.notificationType(notificationType)
				.isRead(isRead)
				.relatedLink(relatedLink)
				.employee(employee)
				.build();
	}
	
	public NotificationDto toDto(Notification notification) {
		return NotificationDto.builder()
				.notification_no(notification.getNotificationNo())
				.receiver_no(notification.getEmployee().getEmpCode())
				.sent_time(notification.getSentTime())
				.notificationContent(notification.getNotificationContent())
				.notificationType(notification.getNotificationType())
				.isRead(notification.getIsRead())
				.relatedLink(notification.getRelatedLink())
				.build();
	}
}
