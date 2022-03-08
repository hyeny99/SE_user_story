package com.example.test;

import com.example.demo.data.Actor;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.container.ActorContainerRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AddActorCommandTest {

    ActorContainerRepo actorContainerRepo;

    String role;
    List<Actor> actors;

    @BeforeEach
    void setUp() {
        actorContainerRepo = new ActorContainerRepo();
        role = "Student";
        actors = new ArrayList<>();
    }

    void registerActor() {
        Actor actor = new Actor(role);
        actors.add(actor);
    }

    @Test
    void call() throws PersistenceException {
        actorContainerRepo.registerActor(actorContainerRepo.createActor(role));
        registerActor();
        Assert.assertEquals(actors.size(), actorContainerRepo.getCurrentList().size(), 1);
        Assert.assertEquals(actors.get(0).getRole(), actorContainerRepo.getCurrentList().get(0).getRole());
    }

    void deregisterActor() {
        if (!actors.isEmpty()) {
            actors.remove(actors.size() - 1);
        }
    }

    @Test
    void undo() throws PersistenceException {
        actorContainerRepo.deregisterActor();
        deregisterActor();
        Assert.assertEquals(actors.size(), actorContainerRepo.getCurrentList().size(), 0);
    }


}