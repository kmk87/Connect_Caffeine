package com.cc.employee.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findByempCode(Long emp_code);
		
		Employee findByempAccount(String emp_account);
		
//		@Query("SELECT new com.cc.employee.domain.EmployeeDto(" +
//	            "e.empCode, e.empName, e.empJobCode, e.empJobName, " +
//	            "g.groupNo, g.groupParentNo, g.groupName) " +
//	            "FROM Employee e " +
//	            "JOIN e.empGroup g " +
//	            "JOIN e.job j")
//		List<EmployeeDto> findAllempList();
//		@Query("SELECT new com.cc.employee.domain.EmployeeDto(e.empCode, e.groupNo, e.empName, e.empJobName, eg.groupParentNo, eg.groupName) " +
//			       "FROM Employee e JOIN e.empGroup eg")
//		List<EmployeeDto> findAllempList();
		@Query("SELECT new com.cc.employee.domain.EmployeeDto(e.empCode, e.empGroup.groupNo, e.empName, e.empJobName, eg.groupParentNo, eg.groupName) " +
			       "FROM Employee e JOIN e.empGroup eg")
			List<EmployeeDto> findAllempList();
}