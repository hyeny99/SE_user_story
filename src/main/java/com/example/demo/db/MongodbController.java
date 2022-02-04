package com.example.demo.db;

import com.example.demo.data.UserStory;
import org.bson.Document;

import java.util.List;

// CRUD
public interface MongodbController {

    public void store(UserStory userStory);
    public List<Document> fetchAll();
    public List<Document> query(String key, String value);
    public void deleteUserStory();
    public void updateUserStory();

}
