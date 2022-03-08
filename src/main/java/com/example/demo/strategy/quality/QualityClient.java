package com.example.demo.strategy.quality;

import com.example.demo.container.ContainerRepo;
import com.example.demo.data.UserStory;
import com.example.demo.container.ActorContainerRepo;
import com.example.demo.strategy.db.PersistenceException;
import org.bson.Document;

import java.util.List;

public class QualityClient {

    private final ContainerRepo containerRepo;
    private final ActorContainerRepo actorContainerRepo;

    private QualityStrategy qualityDescStrategy;

    private String desc;
    double val;

    public QualityClient(QualityStrategy qualityDescStrategy) {
        this.qualityDescStrategy = qualityDescStrategy;

        containerRepo = new ContainerRepo();
        actorContainerRepo = new ActorContainerRepo();
        desc = "";
        val = 100;
    }

    public double calculateQuality(Integer id) throws PersistenceException {
        Document document = containerRepo.get(id);
        if (document != null) {
            if (!((String) document.get("description")).contains("so that")
                    && !((String) document.get("description")).contains("should")){
                this.val -= 30;
                this.desc += this.qualityDescStrategy.insufficientDesc();
            }

            if (document.get("glogerVal") == null || (Double) document.get("glogerVal") == 0.0){
                this.val -= 20;
                this.desc += this.qualityDescStrategy.missingGloger();
            }

            if (document.get("actor") == null ||
                    document.get("actor").equals("") || !actorContainerRepo.isRegistered((String) document.get("actor"))) {
                this.val -= 20;
                this.desc += this.qualityDescStrategy.notRegisteredActor((String) document.get("actor"));
            }

            if (val == 100) {
                this.desc += this.qualityDescStrategy.sufficient();
            }

            return this.val;
        }
        else {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "story not found");
        }
    }

    public String getDesc() {
        return this.desc;
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
