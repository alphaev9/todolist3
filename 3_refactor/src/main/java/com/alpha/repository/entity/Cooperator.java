package com.alpha.repository.entity;

import com.alpha.repository.PersistId;

public class Cooperator {
    private PersistId id;

    private String name;

    private String email;


    public PersistId getId() {
        return id;
    }

    public void setId(PersistId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

}