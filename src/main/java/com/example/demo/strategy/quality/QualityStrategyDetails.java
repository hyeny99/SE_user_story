package com.example.demo.strategy.quality;

import com.example.demo.data.Actor;

public class QualityStrategyDetails implements QualityStrategy{
    @Override
    public String insufficientDesc() {
        return "No written benefit to be identified (-30%)\n";
    }

    @Override
    public String missingGloger() {
        return "No importance value (gloger value) to be identified (-20%)\n";
    }

    @Override
    public String notRegisteredActor(String actor) {
        return "Actor '" + actor + "' is unknown (-20%)\n";
    }

    @Override
    public String sufficient() {
        return "Everything OK";
    }
}
