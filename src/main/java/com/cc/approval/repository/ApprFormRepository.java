package com.cc.approval.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.ApprForm;


public interface ApprFormRepository extends JpaRepository<ApprForm,Long>{
	
	ApprForm findByapprFormNo(Long appr_form_no);

	
	ApprForm findByapprFormType(String appr_form_type);
	
	ApprForm findByapprDocuNo(String appr_docu_no);
	
	@Query("SELECT af.apprDocuNo FROM Approval a JOIN a.apprForm af WHERE a.apprNo = :apprNo")
    String findApprDocuNoByApprovalId(@Param("apprNo") Long apprNo);


}
