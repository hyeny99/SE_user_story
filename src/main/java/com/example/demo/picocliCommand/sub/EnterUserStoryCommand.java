package com.example.demo.picocliCommand.sub;


import com.example.demo.data.Actor;
import com.example.demo.data.UserStory;
import com.example.demo.repo.ActorRepo;
import com.example.demo.repo.ContainerRepo;
import picocli.CommandLine;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "enter",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Enter a user story",
        header = "Enter User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class EnterUserStoryCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            required = true,
            description = "Enter a user story")
    Integer id = null;

    @CommandLine.Option(
            names = {"--desc"},
            required = false,
            description = "Enter a user story")
    String desc = null;

    @CommandLine.Option(
            names = {"--gloger"},
            required = false,
            description = "Enter a user story")
    Double gloger = 0.0;

    @CommandLine.Option(
            names={"--actor"},
            required = false,
            description = "")
    String actor = null;


    private ContainerRepo containerRepo;
    private ActorRepo actorRepo;


    public EnterUserStoryCommand() {
        containerRepo = new ContainerRepo();
        actorRepo = new ActorRepo();
    }

    @Override
    public Integer call() throws Exception {

        try {
            UserStory userStory = new UserStory();

            userStory.setStoryId(id);
            userStory.setDescription(desc);
            userStory.setGlogerVal(gloger);

            if (Objects.nonNull(actor)) {
                Actor a = actorRepo.createActor(actor);
                userStory.setActor(a);
            }

            containerRepo.add(userStory);

        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }

}
