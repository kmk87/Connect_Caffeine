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
import com.cc.approval.domain.ApprovalLine;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	
	Approval findByApprNo(Long appr_no);
	
	Approval findByEmployee_EmpCode(Long empCode);
	
	List<Approval> findByApprState(String appr_state);
	
	Approval findByApprTitle(String appr_title);
	
	Approval findByApprContent(String appr_content);
	
	Approval findByDraftDay(LocalDateTime draft_day);

	
	
	// 전자결재 홈에서 기안문서 리스트
	@Query("SELECT a FROM Approval a WHERE a.employee.empAccount = :memId ORDER BY a.draftDay DESC, a.apprNo DESC") 
	Page<Approval> findByEmployeeAccountOrderByDraftDayDesc(@Param("memId") String memId, Pageable pageable);

    
    // 팀명과 연도를 기준으로 가장 최근의 문서번호 하나만 가져오기
    @Query("SELECT a FROM Approval a WHERE a.docuNo LIKE CONCAT(:teamName, '-%', :year, '-%') ORDER BY a.docuNo DESC")
    List<Approval> findTop1ByTeamNameAndYearOrderByDocuNoDesc(@Param("teamName") String teamName, @Param("year") String year);
    
    // 결재문서함에서 formNo 값 가져오기
    @Query("SELECT al, a.apprForm.apprFormNo FROM ApprovalLine al JOIN al.approval a WHERE a.apprNo = :apprNo")
    List<Object[]> findApprovalLineWithFormNo(@Param("apprNo") Long apprNo);
    
    // 1차 결재 후 2차 결재자에게 리스트 보이기
    @Query("SELECT a FROM Approval a JOIN a.approvalLines al WHERE al.employee.empCode = :empCode AND al.apprOrder = 2 AND al.apprState = 'S'")
    List<Approval> findDocumentsForSecondApprover(@Param("empCode") Long empCode);

    // Approval 항목을 가져오기
    List<Approval> findTop10ByOrderByDraftDayDesc();  
    
    // draftDay 기준 내림차순으로 모든 데이터를 가져오기
    List<Approval> findAllByOrderByDraftDayDesc();

    // Approval 엔티티에서 appr_no 기준으로 내림차순 정렬하기
    List<Approval> findAllByOrderByApprNoDesc();
    
    // 결재 상태와 결재 번호를 기준으로 조회
    Optional<Approval> findByApprStateAndApprNo(String apprState, Long apprNo);
	
    // emp_account를 이용해 로그인한 사용자의 리스트 가져오기
    @Query("SELECT a FROM Approval a WHERE a.employee.empAccount = :empAccount ORDER BY a.apprNo DESC")
    List<Approval> findApprovalsByEmpAccount(@Param("empAccount") String empAccount);

    // 참조자로 등록된 리스트 가져오기
    @Query("SELECT a FROM Approval a " +
    	       "JOIN ApprovalLine al ON a.apprNo = al.approval.apprNo " +
    	       "JOIN Employee e ON al.employee.empCode = e.empCode " +
    	       "WHERE al.apprRole = 2 AND e.empAccount = :empAccount")
    	List<Approval> findReferenceDraftsByEmpAccount(@Param("empAccount") String empAccount);

   
    
    // 내가 기안자인 결재대기건들만 가져오기
    @Query("SELECT a FROM Approval a WHERE a.employee.empCode = :empCode AND a.apprState = 'S'")
    List<Approval> findStandByDraftsByEmpAccount(@Param("empCode") Long empCode);



    






    
    
    
}