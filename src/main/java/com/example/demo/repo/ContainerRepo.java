package com.example.demo.repo;

import com.example.demo.container.Container;
import com.example.demo.container.ContainerException;
import com.example.demo.data.UserStory;
import com.example.demo.persistence.PersistenceException;
import org.bson.Document;

import java.util.List;

public class ContainerRepo {
    private Container container;

    public ContainerRepo() {
        container = Container.getInstance();
    }

    public void add (UserStory userStory) throws ContainerException {
        container.addUserStory(userStory);
        System.out.println("successfully added!");
    }

    public void delete (Integer id) {
        container.deleteUserStory(id);
        System.out.println("successfully deleted!");
    }

    public void update(String key, String value) {
        container.update(key, value);
    }

    public List<UserStory> dump () {
        return container.getCurrentList();
    }

    public List<UserStory> loadAll () throws PersistenceException {
        return container.load();
    }

    // store the list on MongoDB
    public void store() throws PersistenceException {
        container.store();
    }

    // find by id
    public Document get (Integer id) throws PersistenceException {
        return container.findById(id);
    }

    public List<Document> query (String key, String value) throws PersistenceException {
        return container.query(key, value);
    }

    public int size() throws PersistenceException {
        List<UserStory> stories = loadAll();
        return stories.size();
    }


}
