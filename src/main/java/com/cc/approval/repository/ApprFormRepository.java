package com.cc.approval.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.approval.domain.ApprForm;


public interface ApprFormRepository extends JpaRepository<ApprForm,Long>{
	
	ApprForm findByapprFormNo(Long appr_form_no);

	
	ApprForm findByapprFormType(String appr_form_type);
	
	ApprForm findByapprDocuNo(String appr_docu_no);

}
