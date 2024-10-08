package com.cc.approval.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalLine;


public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Long>{
	
	
	
	List<ApprovalLine> findByApproval(Approval approval);
	
	// Approval의 apprNo 필드로 검색
    List<ApprovalLine> findByApproval_ApprNo(Long apprNo);
    
	// 결재자의 이름을 가져오는 쿼리
	@Query("SELECT al, e FROM ApprovalLine al JOIN al.employee e WHERE al.apprLineId = :apprLineId")
	Optional<Object[]> findApprovalLineWithEmployeeByApprLineId(@Param("apprLineId") Long apprLineId);

	@Query("SELECT al.approval FROM ApprovalLine al " +
		       "JOIN al.approval a " +
		       "JOIN ApprovalLine al1 ON al1.approval = al.approval AND al1.apprOrder = 1 " +  // 1차 결재자와 조인
		       "WHERE al.employee.empCode = :empCode " +
		       "AND al.apprState = 'S' " +  // 결재 상태가 S인지 확인
		       "AND (" +
		       "     (al.apprOrder = 1) " +  // 1차 결재자인 경우
		       "     OR " +
		       "     (al.apprOrder = 2 AND al1.apprState = 'C')" +  // 1차 결재자가 승인 완료된 경우만
		       ") " +
		       "ORDER BY al.approval.apprNo DESC, al.approval.draftDay DESC")
		Page<Approval> findPendingApprovalsForCurrentUser(@Param("empCode") Long empCode, Pageable pageable);



	
	// 결재문서함에 상세조회 시 formNo 값 가져오기
	@Query("SELECT a.apprForm.apprFormNo FROM ApprovalLine al JOIN al.approval a WHERE a.apprNo = :apprNo")
	Optional<Long> findApprovalLineWithFormNo(@Param("apprNo") Long apprNo);

	
	// 결재자 순서대로 가져오기
	ApprovalLine findByApprovalApprNoAndApprOrder(Long apprNo, Integer apprOrder);


	// 1차 결재자 문서 조회 쿼리
	@Query("SELECT al.approval FROM ApprovalLine al " +
		       "JOIN al.approval a " +
		       "WHERE al.employee.empCode = :empCode " +
		       "AND al.apprOrder = 1 " + // 1차 결재자인지 확인
		       "AND al.apprState = 'S'") // 결재 대기 상태
		Page<Approval> findPendingApprovalsForFirstApprover(@Param("empCode") Long empCode, Pageable pageable);

	// 2차 결재자 문서 조회 쿼리
	@Query("SELECT al.approval FROM ApprovalLine al " +
		       "JOIN al.approval a " +
		       "JOIN ApprovalLine al1 ON al1.approval = al.approval " +
		       "WHERE al.employee.empCode = :empCode " +
		       "AND al.apprOrder = 2 " + // 2차 결재자인지 확인
		       "AND al1.apprOrder = 1 " + // 1차 결재자의 상태 확인
		       "AND al1.apprState = :firstApprState") // 1차 결재 상태가 'C'인지 확인
		Page<Approval> findPendingApprovalsForSecondApprover(@Param("empCode") Long empCode, @Param("firstApprState") String firstApprState, Pageable pageable);




	// 결재선 정보 조회
	@Query("SELECT al FROM ApprovalLine al WHERE al.approval.docuNo = :docuNo AND al.apprRole = 1 ORDER BY al.apprOrder ASC")
	List<ApprovalLine> findApproversByDocuNo(@Param("docuNo") String docuNo);


    // 참조선 정보 조회
	@Query("SELECT al FROM ApprovalLine al WHERE al.approval.docuNo = :docuNo AND al.apprRole = 2 ORDER BY al.apprOrder ASC")
    List<ApprovalLine> findReferersByDocuNo(@Param("docuNo") String docuNo);
	
	
	
	// 결재상태를 최신 결재번호 기준으로 가져오기
	@Query("SELECT al.apprState FROM ApprovalLine al WHERE al.approval.docuNo = :docuNo ORDER BY al.apprLineId DESC")
	List<String> findLatestApprStateByDocuNo(@Param("docuNo") String docuNo);


	// 결재자로 등록된 모든 문서 리스트 가져오기
	@Query("SELECT al FROM ApprovalLine al JOIN al.approval a WHERE al.employee.empCode = :empCode AND al.apprState IN ('S', 'C', 'R') AND al.apprRole = 1 ORDER BY a.draftDay DESC, al.approval.apprNo DESC")
	List<ApprovalLine> findAllApprovalLinesByEmpCode(@Param("empCode") Long empCode);












	
	
	
}