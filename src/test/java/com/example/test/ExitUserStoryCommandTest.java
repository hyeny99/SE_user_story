package com.example.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitUserStoryCommandTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void call() {
        //SystemLambda.catchSystemExit(() -> {
            //the code under test, which calls System.exit(...);
        //});
        System.out.println("terminate the execution...");
        System.exit(0);
    }
}