package com.cc.approval.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalLineRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.approval.repository.TemporaryStorageRepository;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class ApprovalLineService {
	
	private final ApprovalLineRepository approvalLineRepository;
	
	@Autowired
	public ApprovalLineService(ApprovalLineRepository approvalLineRepository) {
		
	
		this.approvalLineRepository = approvalLineRepository;
	}
	
	

		
}
