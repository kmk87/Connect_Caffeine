package com.cc.notice.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DialectOverride.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="notice")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notice_no")
	private Long noticeNo;
	
	@Column(name="notice_writer_code")
	private Long noticeWriterCode;
	
	@Column(name="notice_writer_name")
	private String noticeWriterName;
	
	@Column(name="notice_title")
	private String noticeTitle;
	
	@Column(name="notice_content")
	private String noticeContent;
	
	@Column(name="notice_status")
	private String noticeStatus;
	
	@Column(name="notice_reg_date")
	@CreationTimestamp
	private LocalDateTime noticeRegDate;
	
	@Column(name="notice_mod_date")
	@UpdateTimestamp
	private LocalDateTime noticeModDate;
	
	@Column(name="notice_comment_status")
	private String noticeCommentStatus;
}
