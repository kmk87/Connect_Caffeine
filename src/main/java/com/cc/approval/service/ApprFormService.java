package com.cc.approval.service;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.ApprForm;
import com.cc.approval.domain.ApprFormDto;
import com.cc.approval.domain.Approval;
import com.cc.approval.repository.ApprFormRepository;
import com.cc.approval.repository.ApprovalRepository;
import com.cc.empGroup.domain.EmpGroupDto;

@Service
public class ApprFormService {
	
	private final ApprFormRepository apprFormRepository;
	private final ApprovalRepository approvalRepository;
	
	@Autowired
	public ApprFormService(ApprFormRepository apprFormRepository,ApprovalRepository approvalRepository) {
		this.apprFormRepository = apprFormRepository;
		this.approvalRepository = approvalRepository;
	}
	
	// 문서 번호 생성
	public String generateDocumentNumber(String groupName) {
		// 그룹 이름 확인
        String groupNameChecked = groupName != null ? groupName : "DEFAULT";
        
        // 현재 연도의 뒤 2자리
        String currentYear = String.format("%02d", Year.now().getValue() % 100);

        // 현재 팀과 연도에 맞는 가장 높은 카운트 조회
        Integer maxCount = apprFormRepository.findMaxCountByTeamAndYear(groupNameChecked, currentYear);

        // 새로운 카운트 계산 (기존 카운트 +1)
        int newCount = (maxCount != null ? maxCount : 0) + 1;

        // 문서번호 생성
        return String.format("%s-%s-%05d", groupNameChecked, currentYear, newCount);
	}
	
	public void saveApprForm(ApprFormDto apprFormDto) {
        // ApprFormDto를 엔티티로 변환하여 저장
        ApprForm apprForm = apprFormDto.toEntity();
        apprFormRepository.save(apprForm);
    }
	
}
