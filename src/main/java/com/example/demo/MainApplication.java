package com.example.demo;


import com.example.demo.commands.CommandController;
import com.example.demo.container.ContainerException;
import com.example.demo.persistence.PersistenceException;
import com.example.demo.picocliCommand.PicocliController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

import java.util.Scanner;


@SpringBootApplication
public class MainApplication  {

//implements CommandLineRunner
	public static void main(String[] args) throws ContainerException, PersistenceException {
//		SpringApplication.run(MainApplication.class, args);
		//commandManual();
		commandPico();


	}

	public static void commandManual() throws ContainerException, PersistenceException {
		CommandController commandController = new CommandController();
		commandController.userInterface();

	}

	public static void commandPico() {
		Scanner stringScanner = new Scanner(System.in);

		while (true) {
			System.out.println("Enter a command: ");
			String arg = stringScanner.nextLine();
			String[] args = arg.split(" ");

			new CommandLine(new PicocliController()).execute(args);
		}

	}


}
