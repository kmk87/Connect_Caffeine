package com.cc.notice.domain;

import java.time.LocalDateTime;

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
public class NoticeCommentDto {
	
	private Long comment_no;
	private Long notice_no;
	private Long comment_writer_code;
	private String comment_writer_name;
	private String comment_content;
	private LocalDateTime comment_reg_date;
	private LocalDateTime comment_mod_date;
	private String comment_status;
	private Long comment_parent_no;
	
	public NoticeComment toEntity(Notice notice) {
		return NoticeComment.builder()
				.commentNo(comment_no)
				.commentWriterCode(comment_writer_code)
				.commentWriterName(comment_writer_name)
				.commentContent(comment_content)
				.commentRegDate(comment_reg_date)
				.commentModDate(comment_mod_date)
				.commentStatus(comment_status)
				.commentParentNo(comment_parent_no)
				.notice(notice)
				.build();
	}
	public NoticeCommentDto toDto(NoticeComment noticeComment) {
		return NoticeCommentDto.builder()
				.comment_no(noticeComment.getCommentNo())
				.comment_writer_code(noticeComment.getCommentWriterCode())
				.comment_writer_name(noticeComment.getCommentWriterName())
				.comment_content(noticeComment.getCommentContent())
				.comment_reg_date(noticeComment.getCommentRegDate())
				.comment_mod_date(noticeComment.getCommentModDate())
				.comment_status(noticeComment.getCommentStatus())
				.comment_parent_no(noticeComment.getCommentParentNo())
				.build();
	}
}
