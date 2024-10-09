package com.cc.approval.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalRepository;


@Service
public class ApprFormService {
	
	private final ApprFormRepository apprFormRepository;
	private final ApprovalRepository approvalRepository;
	
	@Autowired
	public ApprFormService(ApprFormRepository apprFormRepository,ApprovalRepository approvalRepository) {
		this.apprFormRepository = apprFormRepository;
		this.approvalRepository = approvalRepository;
	}
	

	
}