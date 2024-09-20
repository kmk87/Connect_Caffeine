package com.cc.approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.TemporaryStorage;

public interface TemporaryStorageRepository extends JpaRepository<TemporaryStorage,Long>{
	TemporaryStorage findByTemNo(Long tem_no);
	
	//TemporaryStorage findByApproval_ApprNo(Long apprNo);
	
	
	TemporaryStorage findByApprTitle(String appr_title);
	
	TemporaryStorage findByApprContent(String appr_content);
	
	@Query("SELECT t FROM TemporaryStorage t WHERE t.approval.apprNo = :apprNo")
    TemporaryStorage findByApprovalApprNo(@Param("apprNo") Long apprNo);
}
