package com.example.demo.strategy.db;
import com.example.demo.data.UserStory;
import com.example.demo.container.ActorContainerRepo;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class PersistenceStrategyMongoDB implements DBPersistenceStrategy<UserStory> {

    private ActorContainerRepo actorContainerRepo;

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public PersistenceStrategyMongoDB () throws PersistenceException {
        openConnection();
    }

    @Override
    public void openConnection() throws PersistenceException {
        try {
            // Setting up the connection to a local MongoDB with standard port 27017
            // must be started within a terminal with command 'mongodb';
            client = MongoClients.create("mongodb://localhost:27017");

            // Get database 'SE2final' (creates one if not available)
            database = client.getDatabase("SE2final");

            // // Get Collection 'userstory' (creates one if not available)
            collection = database.getCollection("userstory");
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "MongoDB not connected!");
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {
        try {
            this.client.close();
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ClosingFailure,
                    "MongoDB connection cannot be closed!");
        }
    }

    @Override
    public void save(List<UserStory> stories) throws PersistenceException {
        try {
            for (UserStory userStory: stories) {
                if(findById(userStory.getID()) == null){
                    collection.insertOne(userStory.toDocument());
                }
            }
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure,
                    "Cannot be saved to MongoDB!");

        }
    }

    @Override
    public List<UserStory> load() throws PersistenceException {
        try {
            List<Document> documents = new ArrayList<>();
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }

            List<UserStory> userStories = new ArrayList<>();
            actorContainerRepo = new ActorContainerRepo();

            for (Document document : documents) {

                UserStory userStory = new UserStory((Integer) document.get("storyId"), (String) document.get("description"),
                        (Double) document.get("glogerVal"), actorContainerRepo.createActor((String) document.get("actor")), (String) document.get("state"));

                userStories.add(userStory);
            }
            return userStories;

        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    e.getMessage()); //"Data cannot be loaded from MongoDB!"
        }
    }


    public Document findById(Integer id) throws PersistenceException {
        try {
            return collection.find(eq("storyId", id)).first();
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "Data cannot be found in MongoDB!");
        }
    }

    public List<Document> query(String key, String value) throws PersistenceException{
        try{
            List<Document> documents = new ArrayList<>();
            MongoCursor<Document> cursor = collection.find(eq(key, value)).iterator();
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
            return documents;
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "Data cannot be found in MongoDB!");
        }
    }


    @Override
    public void update(int id, String key, String value) throws PersistenceException {
        try {
            if(findById(id) != null) {
                collection.updateOne(eq("storyId", id), set(key, value));
            }
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure, "Data cannot be updated! ");
        }
    }
}
