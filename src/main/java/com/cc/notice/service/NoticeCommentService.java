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
		
		NoticeComment noticeComment = NoticeComment.builder()
				.commentContent(dto.getComment_content())
				.commentWriterCode(dto.getComment_writer_code())
				.commentWriterName(dto.getComment_writer_name())
				.notice(notice)
				.commentStatus("Y")
				.build();
		return noticeCommentRepository.save(noticeComment);
	}
}
