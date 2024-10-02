package com.cc.empGroup.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cc.empGroup.domain.EmpGroup;
import com.cc.empGroup.domain.EmpGroupDto;

public interface EmpGroupRepository extends JpaRepository<EmpGroup,Long>{
	
	EmpGroup findBygroupNo(Long group_no);
	
	// 부서 인원 구하기
	@Query("SELECT SUM(g.groupHeadcount) FROM EmpGroup g WHERE g.groupParentNo = :group_no")
    Long deptHeadcountByGroupNo(@Param("group_no") Long group_no);
	
	
	// 목록에서 부서 인원 구하기
	@Query("SELECT g1 FROM EmpGroup g1 " +
		       "LEFT JOIN EmpGroup g2 ON g1.groupNo = g2.groupParentNo " +
		       "WHERE g1.groupParentNo IS NULL " +
		       "GROUP BY g1.groupNo")
	List<EmpGroup> deptHeadcountList();
	
	// 트리-팀
	@Query("SELECT new com.cc.empGroup.domain.EmpGroupDto(eg.groupNo, eg.groupName) FROM EmpGroup eg "
			+ "WHERE eg.groupParentNo IS NOT NULL")
	List<EmpGroupDto> findAllTeamList();

}