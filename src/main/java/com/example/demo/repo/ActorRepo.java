package com.example.demo.repo;

import com.example.demo.container.ActorContainer;
import com.example.demo.data.Actor;
import com.example.demo.persistence.PersistenceException;

import java.util.List;

public class ActorRepo {
    private String role;
    private ActorContainer actorContainer;

    public ActorRepo() {
        actorContainer = ActorContainer.getInstance();
    }

    public String getRole() {
        return this.role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public Actor createActor(String role) throws PersistenceException {
        return actorContainer.createActor(role);
    }

    public List<Actor> loadAll() {
        return actorContainer.getCurrentList();
    }
}
