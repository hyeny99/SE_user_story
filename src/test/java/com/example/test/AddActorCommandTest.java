package com.example.test;

import com.example.demo.data.Actor;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.repo.ActorRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AddActorCommandTest {

    ActorRepo actorRepo;

    String role;
    List<Actor> actors;

    @BeforeEach
    void setUp() {
        actorRepo = new ActorRepo();
        role = "Student";
        actors = new ArrayList<>();
    }

    void registerActor() {
        Actor actor = new Actor(role);
        actors.add(actor);
    }

    @Test
    void call() throws PersistenceException {
        actorRepo.registerActor(actorRepo.createActor(role));
        registerActor();
        Assert.assertEquals(actors.size(), actorRepo.getCurrentList().size(), 1);
        Assert.assertEquals(actors.get(0).getRole(), actorRepo.getCurrentList().get(0).getRole());
    }

    void deregisterActor() {
        if (!actors.isEmpty()) {
            actors.remove(actors.size() - 1);
        }
    }

    @Test
    void undo() throws PersistenceException {
        actorRepo.deregisterActor();
        deregisterActor();
        Assert.assertEquals(actors.size(), actorRepo.getCurrentList().size(), 0);
    }


}