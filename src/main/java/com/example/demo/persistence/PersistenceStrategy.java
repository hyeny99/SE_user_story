package com.example.demo.persistence;

import org.bson.Document;

import java.util.List;

public interface PersistenceStrategy<UserStory> {
    public void openConnection() throws PersistenceException;
    public void closeConnection() throws PersistenceException;
    public void save(List<UserStory> stories) throws PersistenceException;
    public List<UserStory> load() throws PersistenceException;
    public Document findById(Integer id) throws PersistenceException;
    public List<Document> query(String key, String value) throws PersistenceException;
    public void update(int id, String key, String value) throws PersistenceException;
}
