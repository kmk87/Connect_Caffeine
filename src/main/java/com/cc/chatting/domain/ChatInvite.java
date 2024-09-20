package com.cc.chatting.domain;
import com.cc.employee.domain.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "chat_invite")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class ChatInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_no")
    private Long inviteNo;

    @ManyToOne
    @JoinColumn(name = "emp_code", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "room_no", nullable = false)
    private ChatRoom chatRoom;

}

