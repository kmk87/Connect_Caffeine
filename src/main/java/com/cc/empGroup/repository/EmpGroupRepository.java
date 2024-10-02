
package com.cc.empGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cc.empGroup.domain.EmpGroup;
import com.cc.employee.domain.Employee;

import com.cc.empGroup.domain.EmpGroupDto;


public interface EmpGroupRepository extends JpaRepository<EmpGroup,Long>{
	
	EmpGroup findBygroupNo(Long group_no);
	
	// 부서 인원 구하기
	@Query("SELECT SUM(g.groupHeadcount) FROM EmpGroup g WHERE g.groupParentNo = :group_no")
    Long deptHeadcountByGroupNo(@Param("group_no") Long group_no);

	
	// 부서 번호로 직원 목록을 조회하는 메서드
    List<EmpGroup> findByGroupParentNo(Long groupNo);

//트리-팀
		@Query("SELECT new com.cc.empGroup.domain.EmpGroupDto(eg.groupNo, eg.groupName) FROM EmpGroup eg "
				+ "WHERE eg.groupParentNo IS NOT NULL")
		List<EmpGroupDto> findAllTeamList();

}

