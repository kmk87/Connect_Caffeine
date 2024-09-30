package com.cc.approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.repository.ApprovalRepository;

@Service
public class FileService {
	
	private final ApprovalRepository approvalRepository;
	
	@Autowired
	public FileService(ApprovalRepository approvalRepository) {
		this.approvalRepository = approvalRepository;
	}
	
	
	
	
}
