package com.cc.approval.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.ApprForm;


public interface ApprFormRepository extends JpaRepository<ApprForm,Long>{
	
	ApprForm findByapprFormNo(Long appr_form_no);

	
	ApprForm findByapprFormType(String appr_form_type);
	
	ApprForm findByapprDocuNo(String appr_docu_no);
	
	

	@Query("SELECT COALESCE(MAX(CAST(SUBSTRING(a.apprDocuNo, LENGTH(:groupName) + 6, 3) AS int)), 0) " +
	           "FROM ApprForm a " +
	           "WHERE a.apprDocuNo LIKE CONCAT(:groupName, '-', LPAD(:year, 2, '0'), '-%')")
	    Integer findMaxCountByTeamAndYear(@Param("groupName") String teamName, @Param("year") String year);

}
