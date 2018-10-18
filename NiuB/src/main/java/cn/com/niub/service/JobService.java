package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Job;
import cn.com.niub.dto.JobDto;
import cn.com.niub.jpa.JobRepository;

@Service
public class JobService {

	@Autowired
	JobRepository jobRepository;
	
	public Job findJobById(String id){
		return jobRepository.findOne(id);
	}

	public void saveJob(JobDto dto){
		jobRepository.saveAndFlush(new Job(dto));
	}
}
