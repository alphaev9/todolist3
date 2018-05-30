package com.alpha.repository.mongodb.dao;

import org.springframework.stereotype.Component;

@Component
public class BacklogDAO extends MongoDAO {
    @Override
    protected String getCollectionName() {
        return "backlog";
    }
}
