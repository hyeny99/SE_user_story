package com.example.demo.strategy.quality;

public interface QualityStrategy {
    String insufficientDesc();
    String missingGloger();
    String notRegisteredActor(String actor);
    String sufficient();
}
