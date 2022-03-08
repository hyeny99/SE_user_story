package com.example.demo.commands;

import com.example.demo.container.ContainerException;
import com.example.demo.data.UserStory;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.container.ContainerRepo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class CommandController {

    private final ContainerRepo containerRepo;
    private final Command command;
    BufferedReader reader;
    Scanner numScanner;
    Scanner stringScanner;

    public CommandController () {
        reader = new BufferedReader(new InputStreamReader(System.in));
        numScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);
        containerRepo = new ContainerRepo();
        command = new Command();
        Commands();
    }

    public void Commands() {
        command.generateCommand("help", "HELP", "Show help for the enable options");
        command.generateCommand("enter", "ENTER", "Enter a user story");
        command.generateCommand("store", "STORE", "Store the entered stories");
        command.generateCommand("dump", "DUMP", "Show sorted output of all user stories");
        command.generateCommand("load", "LOAD", "Load previously stored stories");
        command.generateCommand("exit", "EXIT", "Terminate the program");

    }

    public void userInterface() throws ContainerException, PersistenceException {

        while (true) {
            System.out.println("Enter a command: ");
            String cm = stringScanner.nextLine();
            switch (cm) {
                case "help": {
                    String option = "enable options: \n";
                    List<Command> commands = command.getCommands();
                    for (Command c : commands) {
                        option += "-" + c.getArg() + "\t" + c.getName() + "\t\t" +  c.getDesc() + " \n";
                    }
                    System.out.println(option);

                } break;

                case "enter": {
                    System.out.println("Enter a unique id of a user story: ");
                    int id = numScanner.nextInt();

                    System.out.println("Enter description: ");
                    String description = stringScanner.nextLine();

                    System.out.println("Enter a Gloger value: ");
                    double glogerVal = numScanner.nextDouble();

                    UserStory userStory = new UserStory(id, description, glogerVal);
                    containerRepo.add(userStory);
                } break;

                case "store": {
                    containerRepo.store();
                    System.out.println("Successfully saved.");
                } break;

                case "dump": {
//                    System.out.println(containerRepo.loadAll());
                    List<UserStory> stories = containerRepo.dump();
                    for (UserStory story: stories){
                        System.out.println(story);
                    }
                } break;

                case "load": {
                    List<UserStory> userStories = containerRepo.loadAll();
                    for (UserStory userStory: userStories){
                        System.out.println(userStory.toString());
                    }
                } break;

                case "exit": {
                    System.out.println("terminate the execution...");
                    return;
                }
            }
        }
    }
}
