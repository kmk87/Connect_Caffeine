package com.cc.approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.Approval;
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
	
	// ApprForm 테이블에서 데이터를 가져오는 메소드
//	public ApprFormDto getDataInfo(ApprFormDto getApprFormDto) {
//	    Long formDto = getApprFormDto.getAppr_form_no();
//	    
//	    System.out.println("apprFormNo : " + formDto);
//
//	    ApprForm apprForm = apprFormRepository.findByapprFormNo(formDto);
//	    System.out.println("apprForm : " + apprForm);
//
//	    if (apprForm == null) {
//	        // ApprForm이 null일 경우 예외 처리 또는 기본값 반환
//	        throw new RuntimeException("ApprForm not found for appr_form_no: " + formDto);
//	    }
//
//	    ApprFormDto apprFormDto = ApprFormDto.builder()
//	        .appr_form_no(apprForm.getApprFormNo())
//	        .appr_form_type(apprForm.getApprFormType())
//	        .appr_docu_no(apprForm.getApprDocuNo())
//	        .build();
//
//	    return apprFormDto;
//	}
	
	
	
	
	//Approval 테이블의 appr_form_no를 사용하여 appr_docu_no를 가져오는 메소드
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
