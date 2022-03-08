package com.example.test;

import com.example.demo.data.Actor;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.container.ActorContainerRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ActorCommandTest {
    ActorContainerRepo actorContainerRepo;
    List<Actor> actorList;

    Actor actor1;
    Actor actor2;

    @BeforeEach
    void setUp() {
        actorContainerRepo = new ActorContainerRepo();
        actorList = new ArrayList<>();

        actor1 = new Actor("Student");
        actor2 = new Actor("Professor");
        actorList.add(actor1);
        actorList.add(actor2);
    }

    String addActor() {
        String output = "";
        if (actorList.isEmpty())
            output = "no actors given";
        else {
            for(Actor actor : actorList) {
                output = actor.getRole();
            }
        }
        return output;
    }

    @Test
    void call() throws PersistenceException {
        String output = "";
        actorContainerRepo.registerActor(actor1);
        actorContainerRepo.registerActor(actor2);

        List<Actor> actors = actorContainerRepo.getCurrentList();

        if (actors.isEmpty()) {
            output = "no actors given";
        } else {
            for(Actor actor : actors) {
                output = actor.getRole();
            }
        }

        String checkOutput = addActor();
        Assert.assertEquals(output, checkOutput);
    }
}