package com.cc.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;

@Service
public class OrgService {

	private final EmployeeRepository employeeRepository;

    public OrgService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//    public List<TreeMenuDto> getOrgTree() {
//        List<EmpGroup> empGroups = empGroupRepository.findAll();
//        List<TreeMenuDto> treeMenuDtos = new ArrayList<>();
//
//        // for 루프를 이용해 간단하게 TreeMenuDto로 변환
//        for (EmpGroup empGroup : empGroups) {
//            TreeMenuDto dto = new TreeMenuDto(
//                String.valueOf(empGroup.getGroupNo()),  // id
//                empGroup.getGroupParentNo() == null ? "#" : String.valueOf(empGroup.getGroupParentNo()),  // parent
//                empGroup.getGroupName(),  // text
//                true  // 기본으로 열린 상태
//            );
//            treeMenuDtos.add(dto);
//        }
//
//        return treeMenuDtos;
//    }
    public List<Map <String, Object>> getOrgTree(){
    	List<Map <String, Object>> empList = new ArrayList<Map<String,Object>>();
    	List<EmployeeDto> employees = employeeRepository.findAllempList();
        
    	Set<Long> addedGroups = new HashSet<>();
        
    	for (EmployeeDto emp : employees) {
            // 그룹 (부서 및 팀) 노드 추가
            if (!addedGroups.contains(emp.getGroup_no())) {
                Map<String, Object> groupNode = new HashMap<>();
                groupNode.put("id", emp.getGroup_no());
                groupNode.put("parent", emp.getGroup_parent_no() == null ? "#" : emp.getGroup_parent_no());
                groupNode.put("text", emp.getGroup_name());
                empList.add(groupNode);
                addedGroups.add(emp.getGroup_no());
            }
            
            // 직원 노드를 팀(또는 부서) 하위에 추가
            Map<String, Object> empNode = new HashMap<>();
            empNode.put("id", emp.getEmp_code());
            empNode.put("parent", emp.getGroup_no());  // 직원은 팀 또는 부서에 속함
            empNode.put("text", emp.getEmp_name() + " (" + emp.getEmp_job_name() + ")");
            empList.add(empNode);
        }
    	
    	
    	return empList;
    }
    
}

