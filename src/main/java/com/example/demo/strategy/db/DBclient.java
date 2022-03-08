package com.example.demo.strategy.db;

import com.example.demo.data.UserStory;
import org.bson.Document;

import java.util.List;

public class DBclient {

    private DBPersistenceStrategy db;

    public DBclient(DBPersistenceStrategy db) {
        this.db = db;
    }

    public void openConnection() throws PersistenceException {
        this.db.openConnection();
    }

    public void closeConnection() throws PersistenceException {
        this.db.closeConnection();
    }

    public void save(List<UserStory> stories) throws PersistenceException {
        this.db.save(stories);
    }

    public List<UserStory> load() throws PersistenceException {
        return this.db.load();
    }

    public Document findById(Integer id) throws PersistenceException {
        return this.db.findById(id);
    }

    public List<Document> query(String key, String value) throws PersistenceException {
        return this.db.query(key, value);
    }

    public void update(int id, String key, String value) throws PersistenceException {
        this.db.update(id, key, value);
    }
}
