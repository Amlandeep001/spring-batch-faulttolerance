package com.spring.batch.faulttolerance.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.spring.batch.faulttolerance.entity.Customer;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer>
{
	@Override
	public Customer process(Customer customer)
	{
		int age = Integer.parseInt(customer.getAge());// vhjkdfh38497infdhg
		if(age >= 18)
		{
			log.info("Valid Customer {}", customer);
			return customer;
		}
		return null;
	}
}
