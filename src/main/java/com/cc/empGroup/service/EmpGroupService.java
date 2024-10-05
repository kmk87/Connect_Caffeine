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
	

	// 1. 등록
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
	
	// 2-1. 목록

	public List<EmpGroupDto> selectGroupList(){
		
		List<EmpGroup> groupList = null;
		
		groupList = empGroupRepository.findAll();
		
		List<EmpGroupDto> groupDtoList = new ArrayList<EmpGroupDto>();
		
		for(EmpGroup gr : groupList) {
			EmpGroupDto dto = new EmpGroupDto().toDto(gr);
			groupDtoList.add(dto);
		}
		return groupDtoList;
	}
	

	// 2-2. 상세 정보(detail)

	public EmpGroupDto selectGroupOne(Long group_no){
		
		EmpGroup eg = empGroupRepository.findBygroupNo(group_no);
		EmpGroupDto egDto = new EmpGroupDto().toDto(eg);
		
		return egDto;
	}
	

	// dto 찾기
	public EmpGroupDto selectEmpGroupOne(Long group_no) {
		EmpGroup eg = empGroupRepository.findBygroupNo(group_no);
		EmpGroupDto dto = new EmpGroupDto().toDto(eg);
		
		return dto;
	}
	
	// 3. 수정
	public EmpGroup updateGroup(EmpGroupDto dto) {
		
		EmpGroupDto temp = selectEmpGroupOne(dto.getGroup_no());
		
		temp.setGroup_no(dto.getGroup_no());
		temp.setGroup_parent_no(dto.getGroup_parent_no());
		temp.setGroup_name(dto.getGroup_name());
		temp.setGroup_leader_code(dto.getGroup_leader_code());
		temp.setGroup_headcount(dto.getGroup_headcount());
		temp.setGroup_location(dto.getGroup_location());
		temp.setGroup_status(dto.getGroup_status());
		temp.setGroup_level(dto.getGroup_level());
		temp.setGroup_explain(dto.getGroup_explain());
		
		EmpGroup eg = temp.toEntity();
		EmpGroup result = empGroupRepository.save(eg);
		
		return result;
	}
	
	
	// 4-1. 부서 삭제
		public EmpGroup deleteDept(EmpGroupDto dto) {
				
				EmpGroupDto temp1 = selectEmpGroupOne(dto.getGroup_no());
				
				temp1.setGroup_no(dto.getGroup_no());
				temp1.setGroup_parent_no(dto.getGroup_parent_no());
				temp1.setGroup_name(dto.getGroup_name());
				temp1.setGroup_leader_code(dto.getGroup_leader_code());
				temp1.setGroup_headcount(dto.getGroup_headcount());
				temp1.setGroup_location(dto.getGroup_location());
				temp1.setGroup_status(dto.getGroup_status());
				temp1.setGroup_level(dto.getGroup_level());
				temp1.setGroup_explain(dto.getGroup_explain());
				
				// 하위 팀 삭제
				EmpGroupDto temp2 = selectEmpGroupOne(dto.getGroup_parent_no());
				temp2.setGroup_no(dto.getGroup_no());
				temp2.setGroup_parent_no(dto.getGroup_parent_no());
				temp2.setGroup_name(dto.getGroup_name());
				temp2.setGroup_leader_code(dto.getGroup_leader_code());
				temp2.setGroup_headcount(dto.getGroup_headcount());
				temp2.setGroup_location(dto.getGroup_location());
				temp2.setGroup_status(dto.getGroup_status());
				temp2.setGroup_level(dto.getGroup_level());
				temp2.setGroup_explain(dto.getGroup_explain());
				
				EmpGroup eg2 = temp2.toEntity();
				
				EmpGroup result = null;
				
				// 팀 정보 삭제 확인 후 부서 삭제 결과 보냄.
				if(empGroupRepository.save(eg2) != null) {
					
					EmpGroup eg1 = temp1.toEntity();
					result = empGroupRepository.save(eg1);
				}
				return result;
		}
	
	
	// 4-2. 팀 삭제
	public EmpGroup deleteTeam(EmpGroupDto dto) {
			
			EmpGroupDto temp = selectEmpGroupOne(dto.getGroup_no());
			
			temp.setGroup_no(dto.getGroup_no());
			temp.setGroup_parent_no(dto.getGroup_parent_no());
			temp.setGroup_name(dto.getGroup_name());
			temp.setGroup_leader_code(dto.getGroup_leader_code());
			temp.setGroup_headcount(dto.getGroup_headcount());
			temp.setGroup_location(dto.getGroup_location());
			temp.setGroup_status(dto.getGroup_status());
			temp.setGroup_level(dto.getGroup_level());
			temp.setGroup_explain(dto.getGroup_explain());
			
			EmpGroup eg = temp.toEntity();
			EmpGroup result = empGroupRepository.save(eg);
			
			return result;
	}


	// 2-2. 상위 부서명 가져오는 메소드
	public String getParentDeptNameByGroupNo(Long group_no) {
		
		EmpGroup team = empGroupRepository.findBygroupNo(group_no);
		EmpGroup parentDept = empGroupRepository.findBygroupNo(team.getGroupParentNo());
		
		String parentDeptName = parentDept.getGroupName();
		
		return parentDeptName;
	}
	
	// 2-2. 부서명 가져오는 메소드
	public String getDeptOriNameByGroupNo(Long group_no) {
		
		EmpGroup dept = empGroupRepository.findBygroupNo(group_no);
		
		String deptOriName = dept.getGroupName();
		
		return deptOriName;
	}

	// 2-2. 부서 인원 가져오는 메소드
	public Long getDeptHeadcountByGroupNo(Long group_no) {
		
		Long deptHeadcount = empGroupRepository.deptHeadcountByGroupNo(group_no);
		
		return deptHeadcount;
	}


	// 2-2. 책임자 정보 가져오는 메소드
	public Employee getLeaderInfoByGroupNo(Long group_no) {
		
		EmpGroup eg = empGroupRepository.findBygroupNo(group_no);
		Employee leader = employeeRepository.findByempCode(eg.getGroupLeaderCode());

		return leader;
	}
	

	// 2-2. 부서 번호 가져오는 메소드
	public Long getGroupNoByEmpCode(Long group_no) {
		EmpGroup empGroup = empGroupRepository.findById(group_no).orElseThrow();
		Long teamNo = empGroup.getGroupParentNo();
		return teamNo;
	}
	
	// 2-1. 리스트 화면에서 부서 인원 구하기
	public List<EmpGroupDto> getDepartmentHeadcounts() {
		
        List<EmpGroup> groupList = empGroupRepository.deptHeadcountList();
        
        List<EmpGroupDto> groupDtoList = new ArrayList<EmpGroupDto>();
        
        for(EmpGroup gr : groupList) {
        	EmpGroupDto dto = new EmpGroupDto().toDto(gr);
        	groupDtoList.add(dto);
        }
        return groupDtoList;
    }
		
		
	
}