package com.cc.notice.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

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
public class NoticeDto {

	private Long notice_no;
	private Long notice_writer_code;
	private String notice_writer_name;
	private String notice_title;
	private String notice_content;
	private String notice_status;
	private LocalDateTime notice_reg_date;
	private LocalDateTime notice_mod_date;
	private String notice_comment_status;
	
	public Notice toEntity() {
		return Notice.builder()
				.noticeNo(notice_no)
				.noticeWriterCode(notice_writer_code)
				.noticeWriterName(notice_writer_name)
				.noticeTitle(notice_title)
				.noticeContent(notice_content)
				.noticeStatus(notice_status)
				.noticeRegDate(notice_reg_date)
				.noticeModDate(notice_mod_date)
				.noticeCommentStatus(notice_comment_status)
				.build();
	}
	
	public NoticeDto toDto(Notice notice) {
		return NoticeDto.builder()
				.notice_no(notice.getNoticeNo())
				.notice_writer_code(notice.getNoticeWriterCode())
				.notice_writer_name(notice.getNoticeWriterName())
				.notice_title(notice.getNoticeTitle())
				.notice_content(notice.getNoticeContent())
				.notice_status(notice.getNoticeStatus())
				.notice_reg_date(notice.getNoticeRegDate())
				.notice_mod_date(notice.getNoticeModDate())
				.notice_comment_status(notice.getNoticeCommentStatus())
				.build();
	}
}
