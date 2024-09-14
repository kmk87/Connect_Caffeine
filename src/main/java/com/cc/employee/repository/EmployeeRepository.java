package com.cc.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findByempCode(Long emp_code);
		
//		EmployeeDto findDtoByempCode(Long emp_code);
		
		Employee findByempAccount(String emp_account);
		
//		@Query("SELECT e FROM Employee e JOIN e.empGroup g")
//		List<Employee> findAllEmpList();
	
}