package com.example.demo.data;

import com.example.demo.repo.ActorRepo;
import org.bson.Document;

import java.io.Serializable;

public class UserStory implements Serializable {

    private Integer ID = null;
    private String description = null;
    private Double glogerVal = 0.0;
    private Actor actor;
    private String state = null;

    public UserStory() {
        actor = new Actor();
    }

    public UserStory(Integer ID, String description, Double glogerVal, Actor actor, String state) {
        this.ID = ID;
        this.description = description;
        this.glogerVal = glogerVal;
        this.actor = actor;
        this.state = state;
    }

    public UserStory(Integer ID, String description, Double glogerVal, Actor actor ) {
        this.ID = ID;
        this.description = description;
        this.glogerVal = glogerVal;
        this.actor = actor;
    }

    public UserStory(Integer ID, String description, Double glogerVal) {
        this.ID = ID;
        this.description = description;
        this.glogerVal = glogerVal;

    }

    public UserStory(Integer ID, String description) {
        this.ID = ID;
        this.description = description;
    }

    public UserStory(Integer ID, Double glogerVal) {
        this.ID = ID;
        this.glogerVal = glogerVal;
    }

    public Integer getID() {
        return ID;
    }

    public void setStoryId(Integer storyId) {
        this.ID = storyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGlogerVal() {
        return glogerVal;
    }

    public void setGlogerVal(Double glogerVal) {
        this.glogerVal = glogerVal;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "storyId='" + ID + '\'' +
                ", description='" + description + '\'' +
                ", glogerVal='" + glogerVal + '\'' +
                ", actor='" + actor.getRole() + '\'' +
                ", state='" + this.state +
                '}';
    }

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("storyId", this.ID);
        document.append("description" , this.description );
        document.append("glogerVal" , this.glogerVal);
        document.append("actor", this.actor.getRole());
        document.append("state", this.state);
        return document;
    }
}
