package com.alpha.repository.mongodb.dao;

import com.alpha.repository.mongodb.dto.UserDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class UserDAO extends MongoDAO {
    @Override
    protected String getCollectionName() {
        return "user";
    }

    public UserDTO getUser4DTOByName(String userName) {
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        BasicDBObject filter = new BasicDBObject("User.name", userName);
        UserDTO user4DTO = collection.find(filter, UserDTO.class).first();
        return user4DTO;
    }
}
