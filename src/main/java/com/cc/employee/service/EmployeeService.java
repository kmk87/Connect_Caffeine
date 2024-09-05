package com.cc.employee.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cc.employee.domain.Employee;
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

