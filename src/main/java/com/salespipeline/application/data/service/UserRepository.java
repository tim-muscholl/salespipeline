package com.salespipeline.application.data.service;

import com.salespipeline.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getByUsername(String username);

    User getByActivationCode(String activationCode);
}
//getby Username und Activationcode!