package com.cc.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cc.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
	
	Notice findBynoticeNo(Long noticeNo);
	
	@Query("SELECT n FROM Notice n ORDER BY n.noticeRegDate DESC")
	List<Notice> findAllByOrderByNoticeRegDateDesc();
}
