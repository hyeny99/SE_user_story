package com.example.demo.picocliCommand.sub;

import com.example.demo.data.UserStory;
import com.example.demo.repo.ContainerRepo;
import picocli.CommandLine;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
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
    String state = null;


    private ContainerRepo containerRepo;


    public DumpUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            List<UserStory> stories = containerRepo.dump();

            if (Objects.nonNull(state)) {
                for (UserStory story: stories) {
                    if (!story.getState().equals(state)) {
                        stories.remove(story);
                    }
                }
            }

            for (UserStory story: stories) {
                System.out.println(story);
            }

        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
