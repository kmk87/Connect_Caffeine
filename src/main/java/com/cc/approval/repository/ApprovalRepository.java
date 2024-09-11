package com.cc.approval.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.approval.domain.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	
	Approval findByapprNo(Long appr_no);
	
	//Approval findByapprFormNo(Long appr_form_no);
	
	List<Approval> findByApprForm_ApprFormNo(Long apprFormNo);
	
	//List<Approval> findByApprWriterCode(Long apprWriterCode);
	
	Approval findByEmployee_EmpCode(Long empCode);
	
//	Approval findByApprWriterName(String appr_writer_name);
	
	Approval findByApprState(String appr_state);
	
	Approval findByApprTitle(String appr_title);
	
	Approval findByApprContent(String appr_content);
	
	Approval findByDraftDay(LocalDateTime draft_day);
	
	//Approval findByEmployeeApprWriterCode(Long apprWriterCode);
	
	
	
	
	
}