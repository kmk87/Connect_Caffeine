package com.cc.notice.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

@Entity
@Table(name="notice_comment")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Builder
public class NoticeComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="comment_no")
	private Long commentNo;
	
	@ManyToOne
	@JoinColumn(name="notice_no")
	private Notice notice;
	
	@Column(name="comment_writer_name")
	private String commentWriterName;
	
	@Column(name="comment_writer_code")
	private Long commentWriterCode;
	
	@Column(name="comment_content")
	private String commentContent;
	
	@Column(name="comment_reg_date")
	@CreationTimestamp
	private LocalDateTime commentRegDate;
	
	@Column(name="comment_mod_date")
	@UpdateTimestamp
	private LocalDateTime commentModDate;
	
	@Column(name="comment_status")
	private String commentStatus;
	
	@Column(name="comment_parent_no")
	private Long commentParentNo;
}
