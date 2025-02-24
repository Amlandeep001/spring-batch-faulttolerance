package com.spring.batch.faulttolerance.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.spring.batch.faulttolerance.entity.Customer;
import com.spring.batch.faulttolerance.listener.JobCompletionNotificationListener;
import com.spring.batch.faulttolerance.listener.StepSkipListener;
import com.spring.batch.faulttolerance.processor.CustomerItemProcessor;
import com.spring.batch.faulttolerance.reader.CustomerItemReader;
import com.spring.batch.faulttolerance.writer.CustomerItemWriter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig
{
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final CustomerItemReader itemReader;
	private final CustomerItemProcessor itemProcessor;
	private final CustomerItemWriter itemWriter;
	private final StepSkipListener stepSkipListener;
	private final ExceptionSkipPolicy skipPolicy;
	private final JobCompletionNotificationListener jobCompletionNotificationListener;

	@Bean
	Step processCSVStep()
	{
		return new StepBuilder("csv-step", jobRepository)
				.<Customer, Customer>chunk(5, platformTransactionManager)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.faultTolerant()
				// .skipLimit(100)
				// .skip(NumberFormatException.class)
				// .noSkip(IllegalArgumentException.class)
				.listener(stepSkipListener)
				.skipPolicy(skipPolicy)
				.build();
	}

	@Bean
	Job processCSVJob(Step processCSVStep)
	{
		return new JobBuilder("importCustomer", jobRepository)
				.listener(jobCompletionNotificationListener)
				.flow(processCSVStep)
				.end()
				.build();
	}

	/*@Bean
	SkipListener skipListener()
	{
		return new StepSkipListener();
	}*/
}
