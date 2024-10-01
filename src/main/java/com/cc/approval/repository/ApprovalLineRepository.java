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

	// 결재 상태가 'S'이고 현재 사용자가 결재자로 등록된 문서를 조회하는 쿼리
	@Query("SELECT al.approval FROM ApprovalLine al " +
		       "JOIN al.approval a " +
		       "WHERE al.employee.empCode = :empCode " +
		       "AND al.apprState = 'S' " +  // 결재 상태가 S인지 확인
		       "AND (" +
		       "     (al.apprOrder = 1) " +  // 1차 결재자인 경우
		       "     OR " +
		       "     (al.apprOrder = 2 AND EXISTS (SELECT 1 FROM ApprovalLine al1 " +
		       "            WHERE al1.approval.apprNo = al.approval.apprNo " +
		       "            AND al1.apprOrder = 1 " +
		       "            AND al1.apprState = 'C'))" +  // 2차 결재자인 경우, 1차 결재가 완료되었는지 확인
		       ")")
	Page<Approval> findPendingApprovalsForCurrentUser(@Param("empCode") Long empCode, Pageable pageable);

	
	// 결재문서함에 상세조회 시 formNo 값 가져오기
	@Query("SELECT a.apprForm.apprFormNo FROM ApprovalLine al JOIN al.approval a WHERE a.apprNo = :apprNo")
	Optional<Long> findApprovalLineWithFormNo(@Param("apprNo") Long apprNo);

	
	// 결재자 순서대로 가져오기
	ApprovalLine findByApprovalApprNoAndApprOrder(Long apprNo, int apprOrder);


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
		       "WHERE al.employee.empCode = :empCode " +
		       "AND al.apprOrder = 2 " + // 2차 결재자인지 확인
		       "AND EXISTS (SELECT 1 FROM ApprovalLine al1 " +
		       "            WHERE al1.approval.apprNo = al.approval.apprNo " +
		       "            AND al1.apprOrder = 1 " +
		       "            AND al1.apprState = 'C')" +  // 1차 결재자가 결재 완료 상태(C)인지 확인
		       "AND al.apprState = 'S'")  // 결재 상태가 S인지 확인
		Page<Approval> findPendingApprovalsForSecondApprover(@Param("empCode") Long empCode, Pageable pageable);



	
	
	
	
}
