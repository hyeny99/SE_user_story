package com.example.demo;


import com.example.demo.commands.CommandController;
import com.example.demo.container.ContainerException;
import com.example.demo.persistence.PersistenceException;
import com.example.demo.picocliCommand.CommandHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MainApplication  {

//implements CommandLineRunner
	public static void main(String[] args) throws ContainerException, PersistenceException {

		CommandHandler commandHandler = CommandHandler.getInstance();
		commandHandler.startEingabe();

	}

	public static void commandManual() throws ContainerException, PersistenceException {
		CommandController commandController = new CommandController();
		commandController.userInterface();

	}


}
