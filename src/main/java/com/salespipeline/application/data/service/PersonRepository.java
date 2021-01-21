package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}