package com.cc.empGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.group.domain.EmpGroup;

public interface GroupRepository extends JpaRepository<EmpGroup,Long>{
	
	EmpGroup findBygroupNo(Long group_no);
	
	EmpGroup findByEmpCode(Long employeeId);

}
