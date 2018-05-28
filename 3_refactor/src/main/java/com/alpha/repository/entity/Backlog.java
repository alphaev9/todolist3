package com.alpha.repository.entity;

import com.alpha.repository.PersistId;

import java.sql.Date;
import java.util.List;

public class Backlog {
    private PersistId id;
    private String title;
    private String description;
    private String attachment;
    private Date dueTime;
    private BacklogState state;
    private Address address;
    private List<Cooperator> cooperators;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Cooperator> getCooperators() {
        return cooperators;
    }

    public void setCooperators(List<Cooperator> cooperators) {
        this.cooperators = cooperators;
    }

    public PersistId getId() {
        return id;
    }

    public void setId(PersistId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }


    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public BacklogState getState() {
        return state;
    }

    public void setState(BacklogState state) {
        this.state = state;
    }

}