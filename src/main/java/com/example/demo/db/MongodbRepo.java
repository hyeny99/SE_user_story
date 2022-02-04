package com.example.demo.db;

import com.example.demo.data.UserStory;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class MongodbRepo implements MongodbController {

    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;


    public MongodbRepo () {
        // Setting up the connection to a local MongoDB with standard port 27017
        // must be started within a terminal with command 'mongodb';
        client = MongoClients.create("mongodb://localhost:27017");

        // Get database 'SE2final' (creates one if not available)
        database = client.getDatabase("SE2final");

        // // Get Collection 'userstory' (creates one if not available)
        collection = database.getCollection("userstory");
    }

    public MongoClient getMongoClient () {
        return this.client;
    }

    @Override
    public void store(UserStory userStory) {

        if(findById(userStory.getID()) == null){
            collection.insertOne(userStory.toDocument());
        }
    }

    public Document findById(Integer id) {
        return collection.find(eq("storyId", id)).first();
    }

    @Override
    public List<Document> fetchAll() {
        List<Document> documents = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }

        return documents;

    }

    @Override
    public List<Document> query(String key, String value) {
        List<Document> documents = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(eq(key, value)).iterator();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }
        return documents;
    }

    @Override
    public void deleteUserStory() {

    }

    @Override
    public void updateUserStory(int id, String key, String value) {
        if(findById(id) != null) {
            collection.updateOne(eq("storyId", id), set(key, value));
        }
    }
}
