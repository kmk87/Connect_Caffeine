package com.cc.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.notice.domain.Notice;
import com.cc.notice.domain.NoticeComment;
import com.cc.notice.domain.NoticeDto;
import com.cc.notice.repository.NoticeCommentRepository;
import com.cc.notice.repository.NoticeRepository;

@Service
public class NoticeService {
	private final NoticeRepository noticeRepository;
	
	@Autowired
	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	
	public List<NoticeDto> selectNoticeList(){
		List<Notice> noticeList = null;
		
		noticeList = noticeRepository.findAll();
		
		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		for(Notice n : noticeList) {
			NoticeDto dto = new NoticeDto().toDto(n);
			noticeDtoList.add(dto);
		}
		
		return noticeDtoList;
	}
	
	public Notice createNotice(NoticeDto dto) {
		Notice notice = Notice.builder()
				.noticeTitle(dto.getNotice_title())
				.noticeContent(dto.getNotice_content())
				.noticeWriterCode(dto.getNotice_writer_code())
				.noticeWriterName(dto.getNotice_writer_name())
				.noticeCommentStatus(dto.getNotice_comment_status())
				.noticeStatus("Y")
				.build();
		return noticeRepository.save(notice);
	}
	
	public NoticeDto selectNoticeOne(Long notice_no) {
		Notice notice = noticeRepository.findBynoticeNo(notice_no);
		NoticeDto dto = NoticeDto.builder()
				.notice_no(notice.getNoticeNo())
				.notice_title(notice.getNoticeTitle())
				.notice_content(notice.getNoticeContent())
				.notice_writer_code(notice.getNoticeWriterCode())
				.notice_writer_name(notice.getNoticeWriterName())
				.notice_reg_date(notice.getNoticeRegDate())
				.notice_mod_date(notice.getNoticeModDate())
				.notice_comment_status(notice.getNoticeCommentStatus())
				.build();
		return dto;
	}
	
	public Notice updateNotice(NoticeDto dto) {
		NoticeDto noticeDto = selectNoticeOne(dto.getNotice_no());
		noticeDto.setNotice_content(dto.getNotice_content());
		noticeDto.setNotice_comment_status(dto.getNotice_comment_status());
		noticeDto.setNotice_title(dto.getNotice_title());
		noticeDto.setNotice_status("Y");
		Notice notice = noticeDto.toEntity();
		Notice result = noticeRepository.save(notice);
		
		return result;
	}
	
	public Notice deleteNotice(Long notice_no) {
		NoticeDto noticeDto = selectNoticeOne(notice_no);
		noticeDto.setNotice_status("N");
		
		Notice notice = noticeDto.toEntity();
		Notice result = noticeRepository.save(notice);
		
		return result;
	}
}
