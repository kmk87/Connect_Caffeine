package com.cc.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.notice.domain.NoticeComment;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long>{

	List<NoticeComment> findByNoticeNoticeNo(Long noticeNo);
	
	NoticeComment findBycommentNo(Long commentNo);
}
