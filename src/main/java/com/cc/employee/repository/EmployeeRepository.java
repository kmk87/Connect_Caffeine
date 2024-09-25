package com.cc.employee.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cc.employee.domain.Employee;
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findByempCode(Long emp_code);
		
		Employee findByempAccount(String emp_account);
		
}