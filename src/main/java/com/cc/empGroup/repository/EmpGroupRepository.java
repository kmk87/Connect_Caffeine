package com.cc.empGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.empGroup.domain.EmpGroup;



public interface EmpGroupRepository extends JpaRepository<EmpGroup,Long>{
	
	EmpGroup findBygroupNo(Long group_no);
	

}