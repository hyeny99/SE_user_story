package com.example.demo.picocliCommand.sub;


import com.example.demo.data.Actor;
import com.example.demo.container.ActorContainerRepo;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "actors",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Display currently registered actors",
        header = "Actor SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class ActorCommand implements Callable<Integer> {

    private final ActorContainerRepo actorContainerRepo;
    public ActorCommand () {
        actorContainerRepo = new ActorContainerRepo();
    }

    @Override
    public Integer call() throws Exception {

        try {
           List<Actor> actors = actorContainerRepo.getCurrentList();
           if (actors.isEmpty()) {
               System.out.println("no actors given");
           } else {
               for(Actor actor : actors) {
                   System.out.println(actor.getRole());
               }
           }

        } catch(Exception e) {
            throw new Exception();
        }
        return 0;
    }
}


