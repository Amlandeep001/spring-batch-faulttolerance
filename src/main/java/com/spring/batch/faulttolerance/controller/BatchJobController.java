package com.spring.batch.faulttolerance.controller;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.batch.faulttolerance.entity.Customer;
import com.spring.batch.faulttolerance.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class BatchJobController
{
	private final JobLauncher jobLauncher;
	private final Job job;
	private final CustomerRepository repository;

	public BatchJobController(JobLauncher jobLauncher, Job job, CustomerRepository repository)
	{
		this.jobLauncher = jobLauncher;
		this.job = job;
		this.repository = repository;
	}

	@PostMapping(path = "/importData")
	public void startBatch()
	{
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();
		try
		{
			jobLauncher.run(job, jobParameters);
		}
		catch(JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException | JobParametersInvalidException e)
		{
			log.error("Exception occurred during job execution: ", e);
		}
	}

	@GetMapping("/customers")
	public List<Customer> getAll()
	{
		return repository.findAll();
	}
}
