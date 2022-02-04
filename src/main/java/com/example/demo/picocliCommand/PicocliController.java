package com.example.demo.picocliCommand;

import com.example.demo.picocliCommand.sub.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "UserstoryManager",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a user story tool which manages user story activities",
        header = "Userstory manager CLI",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon",
        subcommandsRepeatable = true,
        commandListHeading = "%nSubCommands are: %n",
        subcommands = {
                EnterUserStoryCommand.class,
                StoreUserStoryCommand.class,
                ExitUserStoryCommand.class,
                DumpUserStoryCommand.class,
                LoadUserStoryCommand.class,
                AnalyzeUserStoryCommand.class,
                ActorCommand.class,
                AddActorCommand.class
        }
)
public class PicocliController implements Callable<Integer> {
        final Integer SUCCESS = 0;
        final Integer FAILURE = 1;

        @Override
        public Integer call() throws Exception {
                System.out.println("[manager] Welcome to Userstory Manager");
                return SUCCESS;
        }
}
