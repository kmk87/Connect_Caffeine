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
	
	
	// 전자결재 홈에서 기안문서 리스트
	@Query("SELECT a FROM Approval a WHERE a.employee.empAccount = :memId ORDER BY a.draftDay DESC")
	Page<Approval> findByEmployeeAccountOrderByDraftDayDesc(@Param("memId") String memId, Pageable pageable);
    
    // 팀명과 연도를 기준으로 가장 최근의 문서번호 하나만 가져오기
    @Query("SELECT a FROM Approval a WHERE a.docuNo LIKE CONCAT(:teamName, '-%', :year, '-%') ORDER BY a.docuNo DESC")
    List<Approval> findTop1ByTeamNameAndYearOrderByDocuNoDesc(@Param("teamName") String teamName, @Param("year") String year);
    
    // 결재 상태가 'S'이고 현재 사용자가 결재자로 등록된 문서를 조회하는 쿼리
    @Query("SELECT a FROM Approval a " +
    	       "JOIN ApprovalLine al ON a.apprNo = al.approval.apprNo " +
    	       "JOIN Employee e ON al.employee.empCode = e.empCode " +
    	       "WHERE a.apprState = :apprState " +
    	       "AND e.empAccount = :empAccount")
    	Page<Approval> findPendingApprovalsForApprover(@Param("empAccount") String empAccount, @Param("apprState") String apprState, Pageable pageable);


    // 기안서 삭제 -> 비활성화
    List<Approval> findByIsDeleted(String isDeleted);
	
	
}