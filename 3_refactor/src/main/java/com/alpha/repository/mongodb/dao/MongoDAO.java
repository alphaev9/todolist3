package com.alpha.repository.mongodb.dao;

import com.alpha.repository.PersistId;
import com.alpha.repository.mongodb.DataSource;
import com.alpha.repository.mongodb.MongoID;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public abstract class MongoDAO {
    protected DataSource dataSource;

    public MongoDAO() {
        dataSource=new DataSource("todolist3");
    }

    public <T> List<T> getAll(Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        FindIterable<T> entities = collection.find(clazz);
        MongoCursor<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            list.add(entity);
        }
        return list;
    }

    public <T> T getById(PersistId persistId, Class<T> clazz) {
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        ObjectId id = persistId.getRealId();
        BasicDBObject filter = new BasicDBObject("_id", id);
        T entity = collection.find(filter, clazz).first();
        return entity;
    }

    public <T> PersistId insert(T entity,String name) {
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        Document document = new Document();
        document.append(name, entity);

        collection.insertOne(document);
        ObjectId id = document.getObjectId("_id");
        return new MongoID(id);
    }

    public void delete(PersistId persistId) {
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        ObjectId id = persistId.getRealId();
        BasicDBObject filter = new BasicDBObject("_id", id);
        collection.deleteOne(filter);
    }

    public <T> void update(PersistId persistId, T newEntity,String name) {
        MongoCollection<Document> collection = dataSource.getCollection(getCollectionName());
        ObjectId id = persistId.getRealId();
        Document document = new Document();
        document.append(name, newEntity);
        collection.updateOne(Filters.eq("_id", id), new Document("$set", document));
    }

    protected abstract String getCollectionName();

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
