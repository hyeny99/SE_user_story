package com.example.demo.container;

import com.example.demo.data.Actor;
import com.example.demo.strategy.db.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class ActorContainer {
    private final List<Actor> list;
    private static ActorContainer instance = null;

    public static synchronized ActorContainer getInstance() {
        if (instance == null) {
            instance = new ActorContainer();
            System.out.println("Object of type ActorContainer created!");
        }
        return instance;
    }

    private ActorContainer(){
        System.out.println("Actor container is instantiated! (constructor) ");
        this.list = new ArrayList<Actor>();
    }

    public void addElement(Actor actor) throws PersistenceException {
        for (Actor a : list) {
            if (a.getRole().equals(actor.getRole()))
                throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure,
                        "Already Exist!");
        }
        list.add(actor);
    }

    public List<Actor> getCurrentList() {
        return this.list;
    }

    public Actor createActor(String role) throws PersistenceException {
        return new Actor(role);
    }

    public boolean isActorRegistered(String role) throws PersistenceException {
        for (Actor actor : this.list) {
            String actorRole = actor.getRole();
            if (actorRole.equals(role))
                return true;
        }
        return false;
    }

    public void deregisterActor() throws PersistenceException {
        this.list.remove(this.list.size() - 1);
    }
}
