package com.cc.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.notice.domain.Notice;
import com.cc.notice.domain.NoticeDto;
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
}
