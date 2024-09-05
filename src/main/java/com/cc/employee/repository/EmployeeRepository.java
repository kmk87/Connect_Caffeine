package com.cc.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.employee.domain.Employee;



public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findBymemNo(Long mem_no);
		
		Employee findBymemId(String mem_id);

	

}
