package com.spring.batch.faulttolerance.writer;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.spring.batch.faulttolerance.entity.Customer;
import com.spring.batch.faulttolerance.repository.CustomerRepository;

@Component
@Log4j2
public class CustomerItemWriter implements ItemWriter<Customer>
{
    private final CustomerRepository repository;

    public CustomerItemWriter(CustomerRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception
    {
        log.info("Chunk Size {}", chunk.size());
        log.info("Writer Thread {}", Thread.currentThread().getName());
        repository.saveAll(chunk);
    }
}
