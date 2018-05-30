package com.alpha.repository.mongodb.dto;

import org.bson.types.ObjectId;

import java.util.List;

public class UserDTO {
    private ObjectId id;
    private String name;
    private String password;
    List<ObjectId> ObjectIdsOfBacklog;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ObjectId> getObjectIdsOfBacklog() {
        return ObjectIdsOfBacklog;
    }

    public void setObjectIdsOfBacklog(List<ObjectId> objectIdsOfBacklog) {
        ObjectIdsOfBacklog = objectIdsOfBacklog;
    }
}
