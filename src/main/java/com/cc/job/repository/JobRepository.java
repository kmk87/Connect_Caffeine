package com.cc.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cc.job.domain.Job;

public interface JobRepository extends JpaRepository<Job, String>{
	Job findByjobCode(String job_code);
}
