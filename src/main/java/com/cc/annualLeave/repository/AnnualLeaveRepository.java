package com.cc.annualLeave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.annualLeave.domain.AnnualLeave;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave,Long>{

}
