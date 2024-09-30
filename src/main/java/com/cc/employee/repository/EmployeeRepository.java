package com.cc.employee.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.employee.domain.Employee;
import com.cc.employee.domain.EmployeeDto;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
		
		Employee findByempCode(Long emp_code);
		
		EmployeeDto findDtoByempCode(Long emp_code);
		
		
		Employee findByempAccount(String emp_account);

		
		Employee findByempName(String emp_name);

		// empAccount(사용자 이름)로 emp_code를 찾는 메서드
	    @Query("SELECT e.empCode FROM Employee e WHERE e.empAccount = :empAccount")
	    Long findEmpCodeByEmpName(@Param("empAccount") String empAccount);

		@Query("SELECT e.empName FROM Employee e " +
                 "JOIN Approval a ON a.employee.empCode = e.empCode " +
                 "WHERE e.empAccount = :memId")
      String findEmpNameByMemId(@Param("memId") String memId);
      
     // 사원 리스트
     @Query("SELECT e FROM Employee e ORDER BY e.empHiredate DESC")
     List<Employee> findAllOrderByHiredateDesc();
  
  
		// 사원 번호 추출
		@Query("SELECT CONCAT('b', " +
			       "LPAD(CAST(SUBSTRING(CAST(YEAR(CURRENT_DATE) AS string), 3, 2) AS string), 2, '0'), " +
			       "LPAD(CAST(COUNT(e) + 1 AS string), 3, '0')) " +
			       "FROM Employee e WHERE YEAR(e.empHiredate) = YEAR(CURRENT_DATE)")
		String getInputAccount();
		
		// 트리-팀원
		@Query("SELECT new com.cc.employee.domain.EmployeeDto(e.empGroup.groupNo, e.empCode, e.empName, e.empJobCode, e.empJobName, eg.groupParentNo, eg.groupName) " +
					"FROM Employee e JOIN e.empGroup eg")
		List<EmployeeDto> findAllempList();
		
		
		// 전자결재 관련
		@Query("SELECT e.empName FROM Employee e " +
		           "JOIN Approval a ON a.employee.empCode = e.empCode " +
		           "WHERE e.empAccount = :memId")
		    String findEmpNameByMemId(@Param("memId") String memId);
		

		@Modifying
		@Query("UPDATE Employee e SET e.empSignatureImagePath = :filePath WHERE e.empAccount = :empAccount")
		int updateEmployeeSignatureByAccount(@Param("empAccount") String empAccount, @Param("filePath") String filePath);


		
		
		
		
}