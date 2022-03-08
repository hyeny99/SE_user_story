package com.example.demo.picocliCommand.sub;


import com.example.demo.data.Actor;
import com.example.demo.data.UserStory;
import com.example.demo.picocliCommand.Command;
import com.example.demo.container.ActorContainerRepo;
import com.example.demo.container.ContainerRepo;
import picocli.CommandLine;

import java.util.Objects;
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
public class EnterUserStoryCommand implements Callable<Integer>, Command {

    @CommandLine.Option(
            names = {"--id"},
            required = true,
            description = "Enter a user story")
    Integer id = null;

    @CommandLine.Option(
            names = {"--desc"},
            arity = "1..*",
            required = true,
            description = "Enter a user story")
    String[] desc;

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


    private final ContainerRepo containerRepo;
    private final ActorContainerRepo actorContainerRepo;


    public EnterUserStoryCommand() {
        containerRepo = new ContainerRepo();
        actorContainerRepo = new ActorContainerRepo();
    }

    @Override
    public Integer call() throws Exception {

        try {
            UserStory userStory = new UserStory();

            userStory.setStoryId(id);
            userStory.setDescription(String.join(" ", desc));
            userStory.setGlogerVal(gloger);

            if (Objects.nonNull(actor)) {
                Actor a = actorContainerRepo.createActor(actor);
                userStory.setActor(a);
            }

            containerRepo.add(userStory);

        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }

    @Override
    public void undo() {
        containerRepo.undoEnter();
    }
}
