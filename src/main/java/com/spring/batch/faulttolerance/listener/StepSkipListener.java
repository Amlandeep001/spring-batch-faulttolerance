package com.spring.batch.faulttolerance.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.faulttolerance.entity.Customer;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class StepSkipListener implements SkipListener<Customer, Number>
{
	/* private final ObjectMapper objectMapper;
	
	public StepSkipListener(ObjectMapper objectMapper)
	{
	    this.objectMapper = objectMapper;
	}*/

	@Override // item reader
	public void onSkipInRead(Throwable throwable)
	{
		log.info("A failure on read {} ", throwable.getMessage());
	}

	@Override // item writer
	public void onSkipInWrite(Number item, Throwable throwable)
	{
		log.info("A failure on write {} , {}", throwable.getMessage(), item);
	}

	@SneakyThrows
	@Override // item processor
	public void onSkipInProcess(Customer customer, Throwable throwable)
	{
		log.info("OnSkipInProcess method called");
		log.info("Item {}  was skipped due to the exception  {}", new ObjectMapper().writeValueAsString(customer),
				throwable.getMessage());
	}
}
