package com.example.demo.picocliCommand.sub;


import com.example.demo.picocliCommand.Command;
import com.example.demo.picocliCommand.CommandHandler;
import picocli.CommandLine;

import java.util.Stack;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "undo",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Store a user story",
        header = "Store User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class UndoCommand implements Callable<Integer> {

    CommandHandler commandHandler;
    Stack<Command> commandStack;

    public UndoCommand() {
        commandHandler = CommandHandler.getInstance();
        commandStack = commandHandler.getStack();
    }

    @Override
    public Integer call() throws Exception {

        try {
            if (commandStack.empty()) {
                System.out.println("nothing to undo!");
            } else {
                Command command = commandStack.pop();
                command.undo();
            }
        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
