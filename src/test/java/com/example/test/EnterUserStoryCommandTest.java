package com.example.test;

import com.example.demo.container.ContainerException;
import com.example.demo.data.Actor;
import com.example.demo.data.UserStory;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.container.ActorContainerRepo;
import com.example.demo.container.ContainerRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class EnterUserStoryCommandTest {

    ContainerRepo containerRepo;

    private List<UserStory> userStoryList;
    int userId;
    String desc;
    double gloger;
    String actor;

    @BeforeEach
    void setUp() {
        containerRepo = new ContainerRepo();

        userStoryList = new ArrayList<>();
        userId = 123;
        desc = "first user story to test";
        gloger = 4.2;
        actor = "Student";
    }

    void enterUserStory() {
        UserStory userStory = new UserStory();

        userStory.setStoryId(userId);
        userStory.setDescription(desc);
        userStory.setGlogerVal(gloger);
        userStory.setActor(new Actor(actor));

        userStoryList.add(userStory);
    }

    @Test
    void call() throws ContainerException, PersistenceException {
        UserStory userStory = new UserStory();
        ActorContainerRepo actorContainerRepo = new ActorContainerRepo();

        userStory.setStoryId(userId);
        userStory.setDescription(desc);
        userStory.setGlogerVal(gloger);

        if (Objects.nonNull(actor)) {
            Actor a = actorContainerRepo.createActor(actor);
            userStory.setActor(a);
        }

        containerRepo.add(userStory);
        enterUserStory();

        Assert.assertEquals(containerRepo.dump().size(), userStoryList.size());
    }

    void undoEnterUserStory() {
        if (!userStoryList.isEmpty()) {
            userStoryList.remove(userStoryList.size() - 1);
        }
    }

    @Test
    void undo() {
        containerRepo.undoEnter();
        undoEnterUserStory();

        Assert.assertEquals(containerRepo.dump().size(), userStoryList.size());
    }
}