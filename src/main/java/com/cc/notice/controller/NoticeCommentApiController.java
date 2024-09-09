package com.cc.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.notice.domain.NoticeCommentDto;
import com.cc.notice.service.NoticeCommentService;

@Controller
public class NoticeCommentApiController {
	
	private final NoticeCommentService noticeCommentService;
	
	@Autowired
	public NoticeCommentApiController(NoticeCommentService noticeCommentService) {
		this.noticeCommentService = noticeCommentService;
	}
	
	@ResponseBody
	@PostMapping("/comment")
	public Map<String, String> createComment(NoticeCommentDto dto){
		System.out.println("확인");
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 등록중 오류가 발생하였습니다.");
		
		if(noticeCommentService.createComment(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글이 성공적으로 등록되었습니다.");
		}
		return resultMap;
	}
}
