package com.cc.empGroup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.empGroup.domain.EmpGroup;
import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.repository.EmpGroupRepository;

@Service
public class EmpGroupService {

	private final EmpGroupRepository empGroupRepository;
	
	public EmpGroupService(EmpGroupRepository empGroupRepository) {
		this.empGroupRepository = empGroupRepository;
	}
	
	// 등록
	public EmpGroup createGroup(EmpGroupDto dto) {
		EmpGroup gr = EmpGroup.builder()
				.groupNo(dto.getGroup_no())
				.groupParentNo(dto.getGroup_parent_no())
				.groupName(dto.getGroup_name())
				.groupLeaderCode(dto.getGroup_leader_code())
				.groupRegDate(dto.getGroup_reg_date())
				.groupHeadcount(dto.getGroup_headcount())
				.groupLocation(dto.getGroup_location())
				.groupStatus(dto.getGroup_status())
				.groupLevel(dto.getGroup_level())
				.groupExplain(dto.getGroup_explain())
				.build();
		return empGroupRepository.save(gr);
	}
	
	// 전체 조회
	public List<EmpGroupDto> selectGroupList(){
		// 변수 선언
		List<EmpGroup> groupList = null;
		// 값 가져오기
		groupList = empGroupRepository.findAll();
		// 그릇 만들기
		List<EmpGroupDto> groupDtoList = new ArrayList<EmpGroupDto>();
		// 그릇에 담기
		for(EmpGroup gr : groupList) {
			EmpGroupDto dto = new EmpGroupDto().toDto(gr);
			groupDtoList.add(dto);
		}
		return groupDtoList;
	}
	
	// 상세 정보(detail)
	public EmpGroupDto selectGroupOne(Long group_no){
		EmpGroup eg = empGroupRepository.findBygroupNo(group_no);
		EmpGroupDto egd = null;
		return egd;
	}
	
}
