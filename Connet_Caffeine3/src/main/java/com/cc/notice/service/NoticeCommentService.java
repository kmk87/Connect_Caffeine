package com.cc.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.notice.domain.Notice;
import com.cc.notice.domain.NoticeComment;
import com.cc.notice.domain.NoticeCommentDto;
import com.cc.notice.repository.NoticeCommentRepository;
import com.cc.notice.repository.NoticeRepository;

@Service
public class NoticeCommentService {
	
	private final NoticeCommentRepository noticeCommentRepository;
	private final NoticeRepository noticeRepository;
	
	@Autowired
	public NoticeCommentService(NoticeCommentRepository noticeCommentRepository,
			NoticeRepository noticeRepository) {
		this.noticeCommentRepository = noticeCommentRepository;
		this.noticeRepository = noticeRepository;
	}
	
	public List<NoticeCommentDto> selectCommentList(Long notice_no){
		List<NoticeComment> noticeCommentList = noticeCommentRepository.findByNoticeNoticeNo(notice_no);
		
		
		List<NoticeCommentDto> noticeCommentDtoList = new ArrayList<NoticeCommentDto>();
		for(NoticeComment nc : noticeCommentList) {
			NoticeCommentDto dto = new NoticeCommentDto().toDto(nc);
			noticeCommentDtoList.add(dto);
		}
		
		return noticeCommentDtoList;
	}
	
	public NoticeComment createComment(NoticeCommentDto dto) {
		Long noticeNo = dto.getNotice_no();
		Notice notice = noticeRepository.findById(noticeNo)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid noticeNo: " + noticeNo));
		NoticeComment noticeComment = null;
		NoticeComment result = null;
		if(dto.getComment_parent_no() == null) {
			noticeComment = NoticeComment.builder()
					.commentContent(dto.getComment_content())
					.commentWriterCode(dto.getComment_writer_code())
					.commentWriterName(dto.getComment_writer_name())
					.notice(notice)
					.commentStatus("Y")
					.build();
			result = noticeCommentRepository.save(noticeComment);
		}else {
			noticeComment= NoticeComment.builder()
					.commentContent(dto.getComment_content())
					.commentWriterCode(dto.getComment_writer_code())
					.commentWriterName(dto.getComment_writer_name())
					.notice(notice)
					.commentStatus("Y")
					.commentParentNo(dto.getComment_parent_no())
					.build();
			result = noticeCommentRepository.save(noticeComment);
		}
		return result;
	}
	
	public NoticeComment updateComment(NoticeCommentDto dto) {
		NoticeComment comment = noticeCommentRepository.findBycommentNo(dto.getComment_no());
		
		NoticeCommentDto commentDto = NoticeCommentDto.builder()
				.comment_no(comment.getCommentNo())
				.notice_no(comment.getNotice().getNoticeNo())
				.comment_content(comment.getCommentContent())
				.comment_reg_date(comment.getCommentRegDate())
				.comment_mod_date(comment.getCommentModDate())
				.comment_status(comment.getCommentStatus())
				.comment_writer_code(comment.getCommentWriterCode())
				.comment_writer_name(comment.getCommentWriterName())
				.comment_parent_no(comment.getCommentParentNo())
				.build();
		
		commentDto.setComment_content(dto.getComment_content());
		
		comment = commentDto.toEntity(comment.getNotice());
		
		NoticeComment result = noticeCommentRepository.save(comment);
		
		return result;
	}
	
	public NoticeComment deleteComment(Long comment_no) {
		NoticeComment comment = noticeCommentRepository.findBycommentNo(comment_no);
		
		NoticeCommentDto commentDto = NoticeCommentDto.builder()
				.comment_no(comment.getCommentNo())
				.notice_no(comment.getNotice().getNoticeNo())
				.comment_content(comment.getCommentContent())
				.comment_reg_date(comment.getCommentRegDate())
				.comment_mod_date(comment.getCommentModDate())
				.comment_status(comment.getCommentStatus())
				.comment_writer_code(comment.getCommentWriterCode())
				.comment_writer_name(comment.getCommentWriterName())
				.comment_parent_no(comment.getCommentParentNo())
				.build();
		
		commentDto.setComment_status("N");
		
		comment = commentDto.toEntity(comment.getNotice());
		
		NoticeComment result = noticeCommentRepository.save(comment);
		return result;
	}
}
