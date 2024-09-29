package com.cc.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.empGroup.domain.EmpGroup;
import com.cc.empGroup.domain.EmpGroupDto;
import com.cc.empGroup.repository.EmpGroupRepository;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class OrgService {

	private final EmployeeRepository employeeRepository;
	private final EmpGroupRepository empGroupRepository;

	public OrgService(EmployeeRepository employeeRepository, EmpGroupRepository empGroupRepository) {
		this.employeeRepository = employeeRepository;
		this.empGroupRepository = empGroupRepository;
	}

	List<Map<String, Object>> teamList = null;

	// 1. 팀 노드 추가
	public List<Map<String, Object>> getOrgTeamTree() {

		teamList = new ArrayList<Map<String, Object>>();
		
		List<EmpGroupDto> teams = empGroupRepository.findAllTeamList();
		
		for (EmpGroupDto team : teams) {

			Map<String, Object> teamNode = new HashMap<>();

			teamNode.put("id", team.getGroup_no());
			teamNode.put("parent", "1");
			teamNode.put("text", team.getGroup_name());
			teamNode.put("primaryKey", team.getGroup_no());
			
			
			teamList.add(teamNode);

		}
		return teamList;
	}

	
	// 2. 사원 노드 추가
	public List<Map <String, Object>> getOrgEmpTree(){
    	
    	List<Map <String, Object>> empList = new ArrayList<Map<String, Object>>();
    	List<EmployeeDto> employees = employeeRepository.findAllempList();
    	
    	
    	// 사원의 id는 (그룹 개수+1)에서 시작
    	List<EmpGroup> group = empGroupRepository.findAll();
    	
    	int num = group.size()+1;
    	
    	for (EmployeeDto emp : employees) {
    		
    		Map<String, Object> empNode = new HashMap<>();

    		empNode.put("id", num);
    		empNode.put("parent", emp.getGroup_no());
    		empNode.put("text", emp.getEmp_name() + " (" + emp.getEmp_job_name() + ")");
    		empNode.put("primaryKey", emp.getEmp_code());
    		
    		empList.add(empNode);
    		
    		num++;
    	}
    	return empList;

    }
}
