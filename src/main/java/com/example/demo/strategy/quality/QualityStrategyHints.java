package com.example.demo.strategy.quality;

public class QualityStrategyHints implements QualityStrategy{

    @Override
    public String insufficientDesc() {
        return "Add a sufficient written benefit!\n";
    }

    @Override
    public String missingGloger() {
        return "Add an importance value!\n";
    }

    @Override
    public String notRegisteredActor(String actor) {
        return "Register an actor!\n";
    }

    @Override
    public String sufficient() {
        return "Everything OK";
    }
}
