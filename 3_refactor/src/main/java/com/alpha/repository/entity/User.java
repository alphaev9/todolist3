package com.alpha.repository.entity;

import com.alpha.repository.PersistId;

import java.util.List;

public class User {
    private PersistId id;

    private String name;

    private String password;

    private List<Backlog> backlogs;

    public List<Backlog> getBacklogs() {
        return backlogs;
    }

    public void setBacklogs(List<Backlog> backlogs) {
        this.backlogs = backlogs;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}