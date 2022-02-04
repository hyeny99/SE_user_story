package com.example.demo.picocliCommand.sub;

import com.example.demo.repo.ContainerRepo;
import com.example.demo.repo.QualityRepo;
import picocli.CommandLine;

import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "analyze",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Show sorted output of all user stories",
        header = "Analyze User Story SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Hyewon Jeon")
public class AnalyzeUserStoryCommand implements Callable<Integer> {


    @CommandLine.Option(
            names = {"--id"},
            description = "Enter a unique id of a user story")
    Integer id = null;

    @CommandLine.Option(
            names = {"--all"},
            description = "Enter a unique id of a user story")
    Boolean all = true;

    @CommandLine.Option(
            names = {"--details"},
            required = false,
            description = "")
    Boolean detail;

    @CommandLine.Option(
            names = {"--hints"},
            required = false,
            description = "")
    Boolean hint;


    private final ContainerRepo containerRepo;
    private final QualityRepo qualityRepo;


    public AnalyzeUserStoryCommand() {
        containerRepo = new ContainerRepo();
        qualityRepo = new QualityRepo();
    }

    @Override
    public Integer call() throws Exception {
        try {
            String dialog = "";
            if(Objects.nonNull(id)) {
                double val = qualityRepo.calculateQuality(id);
                String eval = qualityRepo.classify(val);
                dialog += "The user story with the ID number " + id + " is of the following quality: \n";
                dialog += val + " % (" + eval +  ")";

                System.out.println(dialog);

                if (Objects.nonNull(detail)) {
                    String details = "\nDetails: \n" + qualityRepo.getDetails();
                    System.out.println(details);

                    if (Objects.nonNull(hint)) {
                        String hints = "Hints: \n" + qualityRepo.getHint();
                        System.out.println(hints);
                    }
                }

            } else if(Objects.nonNull(all)) {
                double val = qualityRepo.calculateAvg();
                int size = containerRepo.size();
                String eval = qualityRepo.classify(val);

                dialog += "Your " +  size + " user stories are of the following average quality: \n";
                dialog += val + " % (" + eval +  ")";
                System.out.println(val);
            }


        } catch(Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
