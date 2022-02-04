package com.example.demo.picocliCommand;

import com.example.demo.picocliCommand.sub.*;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class CommandHandler {

    private final Stack<Command> stack;
    private static CommandHandler instance = null;

    public static synchronized CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
            System.out.println("Object of type Container created!");
        }
        return instance;
    }

    private CommandHandler(){
        this.stack = new Stack<>();
    }

    public Stack<Command> getStack() {
        return this.stack;
    }

    public void startEingabe()  {

        // Initialisierung der Kommandos
        HashMap<String, Command> commandsMap = new HashMap<>();
        commandsMap.put( "enter", new EnterUserStoryCommand());
        commandsMap.put(  "addElement" , new AddActorCommand());

        Scanner stringScanner = new Scanner(System.in);

        while ( true ) {
            System.out.println("Enter a command: ");
            System.out.print("> ");
            String arg = stringScanner.nextLine();
            String[] args = arg.split(" ");

            CommandLine pico = new CommandLine(new PicocliController());
            pico.execute(args);
            String commandName = args[0];
            Command command = commandsMap.get( commandName );

            if (commandName.equals("enter") || commandName.equals("addElement")){
                stack.push(command);
            }

        }

    }

}
