package com.cc.employee.repository;

import java.util.List;

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
		
		
		// list 화면 조회
//		@Query("SELECT e.empName, e.empHiredate, e.empResign, " +
//			       "d.groupName AS departmentName, t.groupName AS teamName, j.jobName AS jobName " +
//			       "FROM Employee e " +
//			       "JOIN EmpGroup t ON e.groupNo = t.groupNo " +  // 사원과 팀 정보 조인
//			       "JOIN EmpGroup d ON t.groupParentNo = d.groupNo " +  // 팀과 부서 정보 조인
//			       "JOIN Job j ON e.jobCode = j.jobCode " +  // 사원과 직급 정보 조인
//			       "WHERE t.groupParentNo IS NOT NULL")
//			List<Object[]> findEmployeeDetailsWithDeptTeamJob();
}