package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

}