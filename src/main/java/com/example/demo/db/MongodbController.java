package com.example.demo.db;

import com.example.demo.data.UserStory;
import org.bson.Document;

import java.util.List;

// CRUD
public interface MongodbController {

    void store(UserStory userStory);
    List<Document> fetchAll();
    List<Document> query(String key, String value);
    void deleteUserStory();
    void updateUserStory(int id, String key, String value);

}
