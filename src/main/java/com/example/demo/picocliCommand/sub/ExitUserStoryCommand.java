package com.example.demo.picocliCommand.sub;

import com.example.demo.repo.ContainerRepo;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "exit",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Terminate the program",
        header = "Exit User Story Manager SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class ExitUserStoryCommand implements Callable<Integer> {

    @CommandLine.Option(
            names={"-q", "--exit"},
            description = "Terminate the program")
    boolean exit;

    private final ContainerRepo containerRepo;

    public ExitUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            System.out.println("terminate the execution...");
            System.exit(0);
        } catch(Exception e) {
            throw new Exception("Failed to terminate.");
        }
        return 0;
    }

}
