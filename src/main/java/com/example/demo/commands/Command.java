package com.example.demo.commands;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String arg;
    private String name;
    private String desc;
    private final List<Command> commands;

    public Command() {
        commands = new ArrayList<>();
    }

    public Command(String arg, String name, String desc) {
        this.arg = arg;
        this.name = name;
        this.desc = desc;
        commands = new ArrayList<>();
    }

    public void generateCommand (String arg, String name, String desc) {
        Command command = new Command(arg, name, desc);
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getArg() {
        return arg;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
