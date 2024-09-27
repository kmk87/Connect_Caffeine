package com.cc.approval.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	
	Approval findByApprNo(Long appr_no);
	
	//Approval findByApprForm_ApprFormNo(Long apprFormNo);
	
	Approval findByEmployee_EmpCode(Long empCode);
	
	List<Approval> findByApprState(String appr_state);
	
	Approval findByApprTitle(String appr_title);
	
	Approval findByApprContent(String appr_content);
	
	Approval findByDraftDay(LocalDateTime draft_day);
	
	
	
	@Query("SELECT a FROM Approval a WHERE a.employee.empAccount = :memId ORDER BY a.draftDay DESC")
	Page<Approval> findByEmployeeAccountOrderByDraftDayDesc(@Param("memId") String memId, Pageable pageable);
	
	// 가장 최근에 생성된 문서번호 가져오기
    Optional<Approval> findFirstByOrderByDocuNoDesc();

    // 기안서 삭제 -> 비활성화
    List<Approval> findByIsDeleted(String isDeleted);
	
	
}