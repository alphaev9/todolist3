package com.alpha.repository.entity;

import com.alpha.repository.PersistId;

public class Address {
    private PersistId id;

    private String street;

    private Integer number;


    public PersistId getId() {
        return id;
    }

    public void setId(PersistId id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}