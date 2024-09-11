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
	
	// ApprForm 테이블에서 데이터를 가져오는 메소드
	public ApprFormDto getDataInfo(ApprFormDto getApprFormDto) {
		Long apprFormNo = getApprFormDto.getAppr_form_no();
		System.out.println("apprFormNo : "+apprFormNo);
		
		ApprForm apprForm = apprFormRepository.findById(apprFormNo).orElse(null);
		System.out.println("apprForm : "+apprForm);
	
		ApprFormDto apprFormDto = ApprFormDto.builder()
			.appr_form_no(apprForm.getApprFormNo())
			.appr_form_type(apprForm.getApprFormType())
			.appr_docu_no(apprForm.getApprDocuNo())
			.build();
	
		return apprFormDto;
	}
	
	
	
	
	// Approval 테이블의 appr_form_no를 사용하여 appr_docu_no를 가져오는 메소드
//    public String getApprDocuNoByApproval(Long apprNo) {
//        // Approval의 appr_form_no를 기준으로 ApprForm의 appr_docu_no를 가져옴
//        String apprDocuNo = apprFormRepository.findApprDocuNoByApprovalId(apprNo);
//
//        if (apprDocuNo == null) {
//            throw new RuntimeException("Document number not found for approval no: " + apprNo);
//        }
//
//        return apprDocuNo;
//    }
	
	
}
