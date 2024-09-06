package com.cc.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cc.notice.domain.NoticeDto;
import com.cc.notice.service.NoticeService;

@Controller
public class NoticeViewController {
	
	private final NoticeService noticeService;
	
	@Autowired
	public NoticeViewController(NoticeService noticeService) {
		this.noticeService = noticeService;
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
}
