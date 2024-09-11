package com.cc.approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.repository.ApprFormRepository;

@Service
public class ApprFormService {
	
	private final ApprFormRepository apprFormRepository;
	
	@Autowired
	public ApprFormService(ApprFormRepository apprFormRepository) {
		this.apprFormRepository = apprFormRepository;
	}
//	
//	public ApprFormDto getDocuOne(ApprFormDto getApprFormDto) {
//		Long apprFormNo = getApprFormDto.getAppr_form_no();
//		
//		ApprForm apprForm = apprFormRepository.findById(apprFormNo);
//	
//		ApprFormDto apprFormDto = ApprFormDto.builder()
//			.appr_form_no(apprForm.getApprFormNo())
//			.appr_form_type(apprForm.getApprFormType())
//			.appr_docu_no(apprForm.getApprDocuNo())
//			.build();
//	
//		return apprFormDto;
//	}
//	
}
