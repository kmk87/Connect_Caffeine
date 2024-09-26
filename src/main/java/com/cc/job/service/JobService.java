package com.cc.job.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cc.job.domain.Job;
import com.cc.job.domain.JobDto;
import com.cc.job.repository.JobRepository;

@Service
public class JobService {
	private final JobRepository jobRepository;
	
	public JobService(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	// 전체 조회
	public List<JobDto> selectJobList(){
		List<Job> jobList = jobRepository.findAll();
		List<JobDto> jobDtoList = new ArrayList<JobDto>();
		for(Job j : jobList) {
			JobDto dto = new JobDto().toDto(j);
			jobDtoList.add(dto);
		}
		return jobDtoList;
	}
	
	
}
