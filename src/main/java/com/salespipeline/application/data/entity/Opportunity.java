package com.salespipeline.application.data.entity;

import com.salespipeline.application.data.AbstractEntity;

import javax.persistence.Entity;

//Entität Opportunity anlegen
@Entity
public class Opportunity extends AbstractEntity {

    private Integer ido;
    private Integer idc;
    private String title;
    private String category;
    private Integer rating;
    private Integer probability;
    private Integer revenue;

    public Integer getIdo() {
        return ido;
    }
    public void setIdo(Integer ido) {
        this.ido = ido;
    }
    public Integer getIdc() {
        return idc;
    }
    public void setIdc(Integer idc) {
        this.idc = idc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Integer getProbability() {
        return probability;
    }
    public void setProbability(Integer probability) {
        this.probability = probability;
    }
    public Integer getRevenue() {
        return revenue;
    }
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

}
