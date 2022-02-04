package com.example.demo.picocliCommand.sub;


import com.example.demo.data.UserStory;
import com.example.demo.repo.ContainerRepo;
import org.bson.Document;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "state",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Add new actors",
        header = "Actor SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class StateUserStoryCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            required = true,
            description = "Load previously stored stories")
    int id;

    @CommandLine.Option(
            names={"-s", "--state"},
            required = true,
            description = "")
    String state;

    private ContainerRepo containerRepo;


    public StateUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {

        } catch(Exception e) {
            throw new Exception("Failed to update.");
        }
        return 0;
    }



}
