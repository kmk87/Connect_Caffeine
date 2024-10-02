package com.cc.approval.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.ApprForm;

import jakarta.transaction.Transactional;


public interface ApprFormRepository extends JpaRepository<ApprForm,Long>{
	
	ApprForm findByapprFormNo(Long appr_form_no);
	
	ApprForm findByapprFormType(String appr_form_type);
	



	
}
