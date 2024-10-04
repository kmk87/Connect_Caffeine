package com.cc.chatting.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class ChatMessage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_no")
    private Long messageNo;

    @Column(name = "emp_code")
    private Long empCode;

    @Column(name = "room_no")
    private Long roomNo;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "message_date")
    @CreationTimestamp
    private LocalDateTime messageDate;

    // 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "emp_code", referencedColumnName = "emp_code", insertable = false, updatable = false),
        @JoinColumn(name = "room_no", referencedColumnName = "room_no", insertable = false, updatable = false)
    })
    private ChatInvite chatInvite;
}
