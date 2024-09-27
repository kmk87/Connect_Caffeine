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
	Map<Long, Integer> teamIdMap = new HashMap<>(); // 팀의 group_no와 id를 매핑하기 위한 Map

	// 1. 팀 노드 추가
	public List<Map<String, Object>> getOrgTeamTree() {

		teamList = new ArrayList<Map<String, Object>>();
		List<EmpGroupDto> teams = empGroupRepository.findAllTeamList();
		
		
		int num1 = 2; // 1은 루트(전사)
		
		for (EmpGroupDto team : teams) {

			Map<String, Object> teamNode = new HashMap<>();

			teamNode.put("id", num1);
			teamNode.put("parent", "#");
			teamNode.put("text", team.getGroup_name());

			teamList.add(teamNode);
			teamIdMap.put(team.getGroup_no(), num1); // 팀의 group_no와 id를 매핑
			
			num1++;
		}
		System.out.println("서비스의 팀아이디맵: " + teamIdMap);
		System.out.println("서비스의 팀리스트: " + teamList);
		return teamList;
	}

	// 2. 사원 노드 추가
	public List<Map <String, Object>> getOrgEmpTree(){
    	
    	List<Map <String, Object>> empList = new ArrayList<Map<String, Object>>();
    	List<EmployeeDto> employees = employeeRepository.findAllempList();
    	
    	
    	int num2 = teamList.size(); // 팀 리스트의 크기만큼 id 증가
    	
    	for (EmployeeDto emp : employees) {
    		
    		Map<String, Object> empNode = new HashMap<>();
    		
    		Integer parentId = teamIdMap.get(emp.getGroup_no()); // 팀의 group_no와 매핑된 팀의 id 값을 parent로 설정
    		System.out.println("서비스의 부모아이디: " + parentId);
    		if (parentId != null) {
                empNode.put("parent", parentId); 
            } else {
                empNode.put("parent", "#"); // 만약 매칭되는 팀이 없으면 루트로 설정
            }
    		
    		empNode.put("id", num2);
    		empNode.put("text", emp.getEmp_name() + " (" + emp.getEmp_job_name() + ")");
    		
    		empList.add(empNode);
    		
    		num2++;
    	}
    	
    	return empList;

    }
}
