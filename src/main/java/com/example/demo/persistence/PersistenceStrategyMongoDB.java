package com.example.demo.persistence;
import com.example.demo.container.ActorContainer;
import com.example.demo.data.Actor;
import com.example.demo.data.UserStory;
import com.example.demo.db.MongodbRepo;
import com.example.demo.repo.ActorRepo;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PersistenceStrategyMongoDB implements PersistenceStrategy<UserStory> {

    private MongodbRepo mongodbRepo;
    private ActorRepo actorRepo;

    public PersistenceStrategyMongoDB () throws PersistenceException {
        openConnection();
    }

    @Override
    public void openConnection() throws PersistenceException {
        try {
            mongodbRepo = new MongodbRepo();
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "MongoDB not connected!");
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {
        try {
            mongodbRepo.getMongoClient().close();
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ClosingFailure,
                    "MongoDB connection cannot be closed!");
        }
    }

    @Override
    public void save(List<UserStory> stories) throws PersistenceException {
        try {
            for (UserStory story: stories) {
                mongodbRepo.store(story);
            }
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure,
                    "Cannot be saved to MongoDB!");

        }

    }

    @Override
    public List<UserStory> load() throws PersistenceException {
        try {
            List<Document> documents = mongodbRepo.fetchAll();
            List<UserStory> userStories = new ArrayList<>();
            actorRepo = new ActorRepo();

            for (Document document : documents) {

                UserStory userStory = new UserStory((Integer) document.get("storyId"), (String) document.get("description"),
                        (Double) document.get("glogerVal"), actorRepo.createActor((String) document.get("actor")), (String) document.get("state"));

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
            Document document = mongodbRepo.findById(id);
            return document;
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "Data cannot be found in MongoDB!");
        }
    }

    public List<Document> query(String key, String value) throws PersistenceException{
        try{
            return mongodbRepo.query(key, value);
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "Data cannot be found in MongoDB!");
        }
    }


    @Override
    public void update(int id, String key, String value) throws PersistenceException {
        try {
            mongodbRepo.updateUserStory(id, key, value);
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure, "Data cannot be updated! ");
        }
    }
}
