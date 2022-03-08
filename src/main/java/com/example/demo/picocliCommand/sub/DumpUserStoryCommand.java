package com.example.demo.picocliCommand.sub;

import com.example.demo.data.UserStory;
import com.example.demo.container.ContainerRepo;
import picocli.CommandLine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "dump",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Show sorted output of all user stories",
        header = "Dump User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class DumpUserStoryCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-s", "--state"},
            description = "Show sorted output of all user stories")
    String state;


    private final ContainerRepo containerRepo;


    public DumpUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            List<UserStory> stories = containerRepo.dump();

            if (Objects.nonNull(state)) {
                stories = containerRepo.findByState(state);
            }


            for (UserStory story: stories) {
                System.out.println(story);
            }

            System.out.println("Found " + stories.size() + " user story(ies)!\n");

        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
