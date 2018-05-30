package com.alpha.repository.mongodb;

import com.alpha.repository.mongodb.codec.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public class DataSource {
    private String databaseName;

    public DataSource(String databaseName) {
        this.databaseName = databaseName;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new BacklogStateCodec(),new AddressCodec(),new CooperatorCodec(),new User4DTOCodec()),
                CodecRegistries.fromProviders(new BacklogCodecProvider()),
                MongoClient.getDefaultCodecRegistry());
        MongoCollection<Document> collection = database.getCollection(collectionName).withCodecRegistry(codecRegistry);
        return collection;
    }

}
