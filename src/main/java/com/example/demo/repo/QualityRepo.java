package com.example.demo.repo;

import com.example.demo.data.UserStory;
import com.example.demo.persistence.PersistenceException;
import org.bson.Document;

import java.util.List;

public class QualityRepo {
    private final ContainerRepo containerRepo;
    private final ActorRepo actorRepo;
    private String details;
    private String hint;
    double val;

    public QualityRepo() {
        containerRepo = new ContainerRepo();
        actorRepo = new ActorRepo();
        details = "";
        hint = "";
        val = 100;
    }

    public String getDetails() {
        return details;
    }

    public String getHint() {
        return hint;
    }

    public double getVal() {
        return val;
    }

    public double calculateQuality(Integer id) throws PersistenceException {
        Document document = containerRepo.get(id);
        if (document != null) {
            if (document.get("description") == null){
                this.val -= 30;
                this.details += "No written benefit to be identified (-30%)\n";
                this.hint += "Add a written benefit!\n";
            }

            if (document.get("glogerVal") == null || (Double) document.get("glogerVal") == 0.0){
                this.val -= 20;
                this.details += "No importance value (gloger value) to be identified (-20%)\n";
                this.hint += "Add an importance value!\n";
            }

            if (document.get("actor") == null ||
                    document.get("actor").equals("") ||
                    !actorRepo.isRegistered((String) document.get("actor"))) {
                this.val -= 20;
                this.details += "Actor '" + document.get("actor") + "' is unknown (-20%)\n";
                this.hint += "Register an actor!\n";
            }

            if (val == 100) {
                this.details += "Everything OK";
                this.hint += "Everything OK";
            }

            return this.val;
        }
        else {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "story not found");
        }
    }

    public double calculateAvg() throws PersistenceException {
        List<UserStory> userStories = containerRepo.loadAll();
        double vals = 0;
        if (!userStories.isEmpty()) {
            for (UserStory userStory: userStories) {
                double val = calculateQuality(userStory.getID());
                vals += val;
            }

            double value = vals / userStories.size();
            return (double) Math.round(value * 10) / 10;

        }
        else {
            throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable,
                    "Failed to get the average quality value");
        }

    }

    public String classify(double val) {
        String eval = "";
        if (val >= 90) {
            eval = "very good";
        } else if (val >= 80) {
            eval = "good";
        } else if (val >= 70) {
            eval = "satisfactory";
        } else if (val >= 50) {
            eval = "sufficient";
        } else {
            eval = "disqualify";
        }

        return eval;
    }

}
