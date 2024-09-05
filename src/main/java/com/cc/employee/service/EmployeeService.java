package com.cc.employee.service;

import org.springframework.stereotype.Service;

import com.cc.employee.repository.EmployeeRepository;
@Service
public class EmployeeService {
	
//	private final PasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;
	
	public EmployeeService(
			EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	
}
