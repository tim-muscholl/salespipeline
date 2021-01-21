package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Customers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customers, Integer> {

}