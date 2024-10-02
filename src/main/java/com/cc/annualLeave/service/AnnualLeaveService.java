package com.cc.annualLeave.service;

import org.springframework.stereotype.Service;

import com.cc.annualLeave.repository.AnnualLeaveRepository;

@Service
public class AnnualLeaveService {

private final AnnualLeaveRepository annualLeaveRepository;
	
	public AnnualLeaveService(AnnualLeaveRepository annualLeaveRepository) {
		this.annualLeaveRepository = annualLeaveRepository;
	}
	
	
}
