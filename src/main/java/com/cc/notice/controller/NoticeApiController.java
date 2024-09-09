package com.cc.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.notice.domain.NoticeDto;
import com.cc.notice.service.NoticeService;

@Controller
public class NoticeApiController {

	private final NoticeService noticeService;

	@Autowired
	public NoticeApiController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@ResponseBody
	@PostMapping("/notice")
	public Map<String, String> createNotice(NoticeDto dto) {
		System.out.println(dto);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "공지사항 등록중 오류가 발생했습니다.");

		if(noticeService.createNotice(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "공지사항이 성공적으로 등록되었습니다.");
		}
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/notice/{notice_no}")
	public Map<String, String> updateNotice(NoticeDto dto){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "공지사항 수정중 오류가 발생했습니다.");
		
		if(noticeService.updateNotice(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "공지사항이 성공적으로 수정되었습니다.");
		}
		return resultMap;
	}
}
