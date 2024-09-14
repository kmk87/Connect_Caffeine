package com.cc.empGroup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.group.domain.EmpGroup;
import com.cc.group.domain.EmpGroupDto;
import com.cc.group.repository.GroupRepository;

@Service
public class GroupService {

	private final GroupRepository groupRepository;
	
	public GroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
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
		return groupRepository.save(gr);
	}
	
	// 전체 조회
	public List<EmpGroupDto> selectGroupList(){
		// 변수 선언
		List<EmpGroup> groupList = null;
		// 값 가져오기
		groupList = groupRepository.findAll();
		// 그릇 만들기
		List<EmpGroupDto> groupDtoList = new ArrayList<EmpGroupDto>();
		// 그릇에 담기
		for(EmpGroup gr : groupList) {
			EmpGroupDto dto = new EmpGroupDto().toDto(gr);
			groupDtoList.add(dto);
		}
		return groupDtoList;
	}
	
}
