package com.example.demo.picocliCommand.sub;

import com.example.demo.repo.ActorRepo;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "addElement",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Add new actors",
        header = "Actor SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class AddActorCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-a", "--actor"},
            description = "",
            required = true)
    String role;

    private ActorRepo actorRepo;

    public AddActorCommand() {
        actorRepo = new ActorRepo();
    }

    @Override
    public Integer call() throws Exception {

        try {
            actorRepo.createActor(role);
            System.out.println("New actor successfully created!");

        } catch (Exception e) {
            throw new Exception(e);
        }
        return 0;
    }

}
