package com.example.demo.picocliCommand.sub;


import com.example.demo.container.ContainerRepo;
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

    private final ContainerRepo containerRepo;


    public StateUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            if (state.equals("done") || state.equals("progress") || state.equals("todo")) {
                containerRepo.update(id, "state", state);
                System.out.println("State has been successfully updated! \n");
            } else {
                throw new Exception("State not supported.");
            }

        } catch(Exception e) {
            throw new Exception("Failed to update.");
        }
        return 0;
    }



}
