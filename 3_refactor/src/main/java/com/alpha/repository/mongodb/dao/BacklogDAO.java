package com.alpha.repository.mongodb.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mongodb")
public class BacklogDAO extends MongoDAO {
    @Override
    protected String getCollectionName() {
        return "backlog";
    }
}
