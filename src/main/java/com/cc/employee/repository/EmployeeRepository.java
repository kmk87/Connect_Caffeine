package com.cc.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
		
		Employee findByempCode(Long emp_code);
		
		
		EmployeeDto findDtoByempCode(Long emp_code);
		
		
		Employee findByempAccount(String emp_account);

		
		@Query("SELECT e.empName FROM Employee e " +
		           "JOIN Approval a ON a.employee.empCode = e.empCode " +
		           "WHERE e.empAccount = :memId")
		    String findEmpNameByMemId(@Param("memId") String memId);
		
		// 사원 번호 추출
		@Query("SELECT CONCAT('b', " +
			       "LPAD(CAST(SUBSTRING(CAST(YEAR(CURRENT_DATE) AS string), 3, 2) AS string), 2, '0'), " +
			       "LPAD(CAST(COUNT(e) + 1 AS string), 3, '0')) " +
			       "FROM Employee e WHERE YEAR(e.empHiredate) = YEAR(CURRENT_DATE)")
		String getInputAccount();
}