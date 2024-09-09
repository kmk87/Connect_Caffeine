package com.cc.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cc.notice.domain.NoticeCommentDto;
import com.cc.notice.domain.NoticeDto;
import com.cc.notice.service.NoticeCommentService;
import com.cc.notice.service.NoticeService;

@Controller
public class NoticeViewController {
	
	private final NoticeService noticeService;
	private final NoticeCommentService noticeCommentService;
	
	@Autowired
	public NoticeViewController(NoticeService noticeService,
			NoticeCommentService noticeCommentService) {
		this.noticeService = noticeService;
		this.noticeCommentService = noticeCommentService;
	}
	
	@GetMapping("/notice")
	public String noticeList(Model model) {
		List<NoticeDto> resultList = noticeService.selectNoticeList();
		model.addAttribute("resultList", resultList);
		return "notice/list";
	}
	
	@GetMapping("/noticeCreate")
	public String createNoticePage() {
		return "notice/create";
	}
	
	@GetMapping("/notice/detail/{notice_no}")
	public String selectNoticeOne(Model model,
			@PathVariable("notice_no") Long notice_no) {
		NoticeDto noticeDto = noticeService.selectNoticeOne(notice_no);
		List<NoticeCommentDto> noticeCommentDto = noticeCommentService.selectCommentList(notice_no);
		model.addAttribute("dto",noticeDto);
		model.addAttribute("resultList", noticeCommentDto);
		return "notice/detail";
	}
	
	@GetMapping("/notice/update/{notice_no}")
	public String updateNoticePage(Model model,
			@PathVariable("notice_no") Long notice_no) {
		NoticeDto dto = noticeService.selectNoticeOne(notice_no);
		model.addAttribute("dto",dto);
		return "/notice/update";
	}
	
}
