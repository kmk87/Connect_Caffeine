package com.cc.employee.service;

import org.springframework.stereotype.Service;

import com.cc.calendar.domain.CalendarDto;
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
	
	public EmployeeDto findByempName(String emp_name) {
		Employee employee = employeeRepository.findByempName(emp_name);
		
		EmployeeDto dto = EmployeeDto.builder()
						.emp_code(employee.getEmpCode())
						.build();
		return dto;
	}
	
	 public Long findEmpCodeByEmpName(String empAccount) {
	        return employeeRepository.findEmpCodeByEmpName(empAccount);
	    }
	
	// empCode를 이용해 Employee 객체를 조회하는 메서드
	    public Employee findByEmpCode(Long empCode) {
	        return employeeRepository.findByempCode(empCode);   
	    }
	    
	 // 그룹 번호 가져오는 메소드
	 		public Long getGroupNoByEmpCode(Long emp_code) {
	 			Employee emp = employeeRepository.findById(emp_code).orElseThrow();
	 			Long groupNo = emp.getEmpGroup().getGroupNo();
	 			return groupNo;
	 		}

	
	
	
}
