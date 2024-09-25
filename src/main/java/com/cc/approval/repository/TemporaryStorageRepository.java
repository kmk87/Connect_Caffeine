package com.cc.approval.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.TemporaryStorage;

public interface TemporaryStorageRepository extends JpaRepository<TemporaryStorage,Long>{
	TemporaryStorage findByTemNo(Long tem_no);
	
	//TemporaryStorage findByApproval_ApprNo(Long apprNo);
	
	
	TemporaryStorage findByApprTitle(String appr_title);
	
	TemporaryStorage findByApprContent(String appr_content);
	
	
	
	
	@Query("SELECT a FROM TemporaryStorage a WHERE a.employee.empAccount = :memId ORDER BY a.temNo DESC")
	Page<TemporaryStorage> findTop5ByEmployeeEmpAccountOrderByTemNoDesc(@Param("memId") String memId, Pageable pageable);
	
	
}
