package com.example.demo.container;

import com.example.demo.data.Actor;
import com.example.demo.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class ActorContainer {
    private List<Actor> list = null;
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

    private void addElement(Actor actor) throws PersistenceException {
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
        Actor actor = new Actor(role);
        //addElement(actor);
        return actor;
    }
}
