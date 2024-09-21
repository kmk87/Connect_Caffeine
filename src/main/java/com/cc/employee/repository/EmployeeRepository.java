package com.cc.employee.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.employee.domain.Employee;
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findByempCode(Long emp_code);
		
		Employee findByempAccount(String emp_account);
		
		Employee findByempName(String emp_name);

		// empAccount(사용자 이름)로 emp_code를 찾는 메서드
	    @Query("SELECT e.empCode FROM Employee e WHERE e.empAccount = :empAccount")
	    Long findEmpCodeByEmpName(@Param("empAccount") String empAccount);
		

}