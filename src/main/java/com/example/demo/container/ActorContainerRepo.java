package com.example.demo.container;

import com.example.demo.container.ActorContainer;
import com.example.demo.data.Actor;
import com.example.demo.strategy.db.PersistenceException;

import java.util.List;

public class ActorContainerRepo {
    private String role;
    private final ActorContainer actorContainer;

    public ActorContainerRepo() {
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

    public void registerActor(Actor actor) throws PersistenceException {
        actorContainer.addElement(actor);
    }

    public List<Actor> getCurrentList() {
        return actorContainer.getCurrentList();
    }

    public boolean isRegistered(String role) throws PersistenceException {
        return actorContainer.isActorRegistered(role);
    }

    public void deregisterActor() throws PersistenceException {
        actorContainer.deregisterActor();
    }
}
