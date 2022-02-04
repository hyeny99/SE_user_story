package com.example.demo.picocliCommand.sub;

import com.example.demo.data.UserStory;
import com.example.demo.repo.ContainerRepo;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "store",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Store a user story",
        header = "Store User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class StoreUserStoryCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-s", "--store"},
            description = "Store user story(ies)"
    )
    boolean store;

    private ContainerRepo containerRepo;

    public StoreUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            containerRepo.store();
            System.out.println("Successfully saved.");

        } catch(Exception e) {
            throw new Exception("Failed to save.");
        }
        return 0;
    }
}
