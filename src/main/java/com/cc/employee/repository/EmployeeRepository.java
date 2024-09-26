package com.cc.employee.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.employee.domain.Employee;
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
		
		Employee findByempCode(Long emp_code);
		
		Employee findByempAccount(String emp_account);
		
		@Query("SELECT e.empName FROM Employee e " +
		           "JOIN Approval a ON a.employee.empCode = e.empCode " +
		           "WHERE e.empAccount = :memId")
		    String findEmpNameByMemId(@Param("memId") String memId);
		
		@Modifying
		@Query("UPDATE Employee e SET e.empSignatureImagePath = :filePath WHERE e.empAccount = :empAccount")
		int updateEmployeeSignatureByAccount(@Param("empAccount") String empAccount, @Param("filePath") String filePath);

		// empAccount로 empCode 가져오기
//	    @Query("SELECT e.empCode FROM Employee e WHERE e.empAccount = :empAccount")
//	    Long findEmpCodeByEmpAccount(@Param("empAccount") String empAccount);
		
		
		
}