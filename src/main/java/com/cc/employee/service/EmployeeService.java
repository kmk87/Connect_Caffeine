package com.cc.employee.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;
import com.cc.employee.repository.EmployeeRepository;
@Service
public class EmployeeService {
	
//	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService(
			EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	// 팀명 가져오기
	public List<String> getDataInfoName() {
        // 모든 Employee 데이터를 가져온 후 필터링
        List<Employee> employees = employeeRepository.findAll();

        List<String> groupNames = new ArrayList<>();
        for (Employee emp : employees) {
            // emp.getEmpGroup()이 null이 아니고 group_parent_no도 null이 아닌 경우
            if (emp.getEmpGroup() != null && emp.getEmpGroup().getGroupParentNo() != null) {
                groupNames.add(emp.getEmpGroup().getGroupName()); // group_name을 추가
            }
        }
        return groupNames;
	
	}
	
	// 로그인한 사용자의 팀명 가져오기
    public String getUserTeamName(String username) {
        Employee employee = employeeRepository.findByempAccount(username);
        // 해당 사용자의 그룹이 존재하고, group_parent_no가 있는 경우에만 팀명 반환
        if (employee != null && employee.getEmpGroup() != null && employee.getEmpGroup().getGroupParentNo() != null) {
            return employee.getEmpGroup().getGroupName();
        }
        return "DEFAULT"; // 기본값 설정
    }
	
}
