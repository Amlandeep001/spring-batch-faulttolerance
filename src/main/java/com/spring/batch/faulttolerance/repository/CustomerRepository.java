package com.spring.batch.faulttolerance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.batch.faulttolerance.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
}
