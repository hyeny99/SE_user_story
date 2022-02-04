package com.example.demo.persistence;

import org.bson.Document;

import java.util.List;

public interface PersistenceStrategy<UserStory> {
    void openConnection() throws PersistenceException;
    void closeConnection() throws PersistenceException;
    void save(List<UserStory> stories) throws PersistenceException;
    List<UserStory> load() throws PersistenceException;
    Document findById(Integer id) throws PersistenceException;
    List<Document> query(String key, String value) throws PersistenceException;
    void update(int id, String key, String value) throws PersistenceException;
}
