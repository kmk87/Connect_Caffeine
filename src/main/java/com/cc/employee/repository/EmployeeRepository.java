package com.cc.employee.repository;
import java.util.List;

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
		
	 // EmpGroup의 groupNo로 직원 목록을 조회하는 메서드
	    List<Employee> findByEmpGroup_GroupNo(Long groupNo);
	    
	 // EmpGroup의 parent_no를 통해 직원 목록을 조회하는 메서드
	    List<Employee> findByEmpGroup_GroupParentNo(Long groupParentNo);
}