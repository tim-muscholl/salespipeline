package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class CustomersService extends CrudService<Customers, Integer> {

    private CustomersRepository repository;

    public CustomersService(@Autowired CustomersRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CustomersRepository getRepository() {
        return repository;
    }

}
