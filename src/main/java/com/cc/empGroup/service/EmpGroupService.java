package com.cc.empGroup.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.empGroup.domain.EmpGroup;
import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.repository.EmpGroupRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class EmpGroupService {
	
	private final EmployeeRepository employeeRepository;
	private final EmpGroupRepository empGroupRepository;
	
	public EmpGroupService(EmployeeRepository employeeRepository, EmpGroupRepository empGroupRepository) {
		this.employeeRepository = employeeRepository;
		this.empGroupRepository = empGroupRepository;
	}
	
	// 등록
	public EmpGroup createGroup(EmpGroupDto dto) {
		
		System.out.println("서비스의 dto"+dto);
		EmpGroup gr = EmpGroup.builder()
				.groupNo(dto.getGroup_no())
				.groupParentNo(dto.getGroup_parent_no())
				.groupName(dto.getGroup_name())
				.groupLeaderCode(dto.getGroup_leader_code())
//				.groupRegDate(dto.getGroup_reg_date())
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
		EmpGroupDto egDto = new EmpGroupDto().toDto(eg);
		
		return egDto;
	}
	
	// 상위 부서명 가져오는 메소드
	public String getParentDeptNameByGroupNo(Long group_no) {
		
		EmpGroup team = empGroupRepository.findBygroupNo(group_no);
		EmpGroup parentDept = empGroupRepository.findBygroupNo(team.getGroupParentNo());
		
		String parentDeptName = parentDept.getGroupName();
		
		return parentDeptName;
	}
	
	// 부서명 가져오는 메소드
	public String getDeptOriNameByGroupNo(Long group_no) {
		
		EmpGroup dept = empGroupRepository.findBygroupNo(group_no);
		
		String deptOriName = dept.getGroupName();
		
		return deptOriName;
	}
	
	// 부서 인원 가져오는 메소드
	public Long getDeptHeadcountByGroupNo(Long group_no) {
		
		Long deptHeadcount = empGroupRepository.deptHeadcountByGroupNo(group_no);
		
		return deptHeadcount;
	}
	
	// 책임자 정보 가져오는 메소드
	public Employee getLeaderInfoByGroupNo(Long group_no) {
		
		EmpGroup eg = empGroupRepository.findBygroupNo(group_no);
		Employee leader = employeeRepository.findByempCode(eg.getGroupLeaderCode());

		return leader;
	}
	
}
