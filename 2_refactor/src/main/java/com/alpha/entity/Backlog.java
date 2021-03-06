package com.alpha.entity;

import java.sql.Date;
import java.util.List;

public class Backlog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.attachment
     *
     * @mbggenerated
     */
    private String attachment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.dueTime
     *
     * @mbggenerated
     */
    private Date dueTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.state
     *
     * @mbggenerated
     */
    private BacklogState state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column backlog.userId
     *
     * @mbggenerated
     */
    private Integer userid;

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

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.id
     *
     * @return the value of backlog.id
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.id
     *
     * @param id the value for backlog.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.title
     *
     * @return the value of backlog.title
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.title
     *
     * @param title the value for backlog.title
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.description
     *
     * @return the value of backlog.description
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.description
     *
     * @param description the value for backlog.description
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.attachment
     *
     * @return the value of backlog.attachment
     * @mbggenerated
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.attachment
     *
     * @param attachment the value for backlog.attachment
     * @mbggenerated
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.dueTime
     *
     * @return the value of backlog.dueTime
     * @mbggenerated
     */
    public Date getDueTime() {
        return dueTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.dueTime
     *
     * @param dueTime the value for backlog.dueTime
     * @mbggenerated
     */
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.state
     *
     * @return the value of backlog.state
     * @mbggenerated
     */
    public BacklogState getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.state
     *
     * @param state the value for backlog.state
     * @mbggenerated
     */
    public void setState(BacklogState state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column backlog.userId
     *
     * @return the value of backlog.userId
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column backlog.userId
     *
     * @param userid the value for backlog.userId
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}