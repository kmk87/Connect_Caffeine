package com.cc.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PostMapping("/commentCreate")
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
	
	@ResponseBody
	@PostMapping("/commentUpdate/{comment_no}")
	public Map<String, String> updateComment(NoticeCommentDto dto){
		System.out.println(dto);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 수정중 오류가 발생하였습니다.");
		if(noticeCommentService.updateComment(dto) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글이 성공적으로 수정되었습니다.");
		}
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/commentDelete/{comment_no}")
	public Map<String, String> deleteComment(@PathVariable("comment_no") Long comment_no){
		System.out.println(comment_no);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "댓글 삭제중 오류가 발생하였습니다.");
		if(noticeCommentService.deleteComment(comment_no) != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "댓글이 성공적으로 수정되었습니다.");
		}
		return resultMap;
	}
}
