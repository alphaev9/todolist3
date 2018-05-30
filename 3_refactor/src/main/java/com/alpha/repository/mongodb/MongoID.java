package com.alpha.repository.mongodb;

import com.alpha.repository.PersistId;
import org.bson.types.ObjectId;

public class MongoID implements PersistId {
    private ObjectId id;

    public MongoID(ObjectId id) {
        this.id = id;
    }

    @Override
    public ObjectId getRealId() {
        return id;
    }
}
