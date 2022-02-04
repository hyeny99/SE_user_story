package com.example.demo.picocliCommand.sub;


import com.example.demo.data.UserStory;
import com.example.demo.repo.ContainerRepo;
import org.bson.Document;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "load",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Load previously stored stories",
        header = "Load User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class LoadUserStoryCommand implements Callable<Integer> {



    private ContainerRepo containerRepo;


    public LoadUserStoryCommand() {
        containerRepo = new ContainerRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            List<UserStory> userStories = containerRepo.loadAll();
            for (UserStory userStory: userStories){
                System.out.println(userStory.toString());
            }

        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
