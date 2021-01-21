package com.salespipeline.application.data.entity;

import com.salespipeline.application.data.AbstractEntity;

import javax.persistence.Entity;

//Entität Customer anlegen
@Entity
public class Customers extends AbstractEntity {

    private Integer idc;
    private String firstName;
    private String lastName;
    private String email;
    private String jobtitle;
    private String businessphone;
    private String mobilephone;
    private String adress;
    private String city;
    private String state;
    private String country;
    private String zip;

    public Integer getIdc() {
        return idc;
    }
    public void setIdc(Integer idc) {
        this.idc = idc;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getJobtitle() {
        return jobtitle;
    }
    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }
    public String getBusinessphone() {
        return businessphone;
    }
    public void setBusinessphone(String businessphone) {
        this.businessphone = businessphone;
    }
    public String getMobilephone() {
        return mobilephone;
    }
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

}
