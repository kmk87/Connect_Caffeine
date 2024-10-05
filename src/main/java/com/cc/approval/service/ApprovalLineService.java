package com.cc.approval.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalLine;
import com.cc.approval.repository.ApprovalLineRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;



@Service
public class ApprovalLineService {
	
	private final ApprovalLineRepository approvalLineRepository;
	
	@Autowired
	public ApprovalLineService(ApprovalLineRepository approvalLineRepository) {
		
	
		this.approvalLineRepository = approvalLineRepository;
	}
	
	

		
}