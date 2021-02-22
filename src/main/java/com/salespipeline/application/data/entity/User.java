package com.salespipeline.application.data.entity;

import com.salespipeline.application.data.AbstractEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
//Entität User anlegen
@Entity
public class User extends AbstractEntity {

    private String username;
    private String passwordSalt;
    private String passwordHash;
    private Role role;
    //Activation code und boolean um zu sehen ob aktiv oder nicht.
    private String activationCode;
    private boolean active;

    public User() {
    }


    public User(String username, String password, Role role) {
        this.username = username;
        this.role = role;
        this.passwordSalt = RandomStringUtils.random(32); //Salt = rohes Passwort
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt); //gehashed
        this.activationCode = RandomStringUtils.randomAlphanumeric(32); //Buchstaben und Zahlen für geringere Wahrscheinlichkeit für gleiche Codes
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Passwort

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    //Rolle

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //Activation Code

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
