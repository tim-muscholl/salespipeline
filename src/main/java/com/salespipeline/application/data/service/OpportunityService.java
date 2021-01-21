package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Opportunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class OpportunityService extends CrudService<Opportunity, Integer> {

    private OpportunityRepository repository;

    public OpportunityService(@Autowired OpportunityRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OpportunityRepository getRepository() {
        return repository;
    }

}
