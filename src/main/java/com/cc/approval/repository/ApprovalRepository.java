package com.cc.approval.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	
	Approval findByapprNo(Long appr_no);
	
	Approval findByApprForm_ApprFormNo(Long apprFormNo);
	
	Approval findByEmployee_EmpCode(Long empCode);
	
	Approval findByApprState(String appr_state);
	
	Approval findByApprTitle(String appr_title);
	
	Approval findByApprContent(String appr_content);
	
	Approval findByDraftDay(LocalDateTime draft_day);
	
	// Employee의 empAccount 필드를 기준으로 Approval을 조회
    @Query("SELECT a FROM Approval a WHERE a.employee.empAccount = :memId") 
    List<Approval> findByEmployeeAccount(@Param("memId") String memId);
	
	
	
}