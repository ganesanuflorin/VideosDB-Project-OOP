package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        String message;

        for (ActionInputData actionInputData : input.getCommands()) {
            if (actionInputData.getActionType().equals("command")) {
                if (actionInputData.getType().equals("favorite")) {
                     message = UserInputData.favorite(actionInputData.getUsername(),
                             input, actionInputData.getTitle());
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getType().equals("view")) {
                    message = UserInputData.view(actionInputData.getUsername(),
                            input, actionInputData.getTitle());
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getType().equals("rating")) {
                    message = UserInputData.rating(actionInputData.getUsername(), input,
                            actionInputData.getTitle(), actionInputData.getGrade(),
                            actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
            }
            if (actionInputData.getActionType().equals("query")) {
                if (actionInputData.getCriteria().equals("average")) {
                    message = Query.average(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("awards")) {
                    message = Query.awards(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("filter_description")) {
                    message = Query.filterDescription(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("ratings")) {
                    message = Query.queryRating(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("favorite")) {
                    message = Query.queryFavorite(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("most_viewed")) {
                    message = Query.queryMostViewed(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("longest")) {
                    message = Query.queryDuration(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getCriteria().equals("num_ratings")) {
                    message = Query.users(input, actionInputData);
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
            }
            if (actionInputData.getActionType().equals("recommendation")) {
                if (actionInputData.getType().equals("standard")) {
                    message = Recommendation.recomStandard(input, actionInputData.getUsername());
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
                if (actionInputData.getType().equals("best_unseen")) {
                    message = Recommendation.recomBestUnseen(input, actionInputData.getUsername());
                    JSONObject jsonObject = fileWriter.writeFile(actionInputData.getActionId(),
                            message);
                    arrayResult.add(jsonObject);
                }
            }
        }


        //TODO add here the entry point to your implementation

        fileWriter.closeJSON(arrayResult);
    }
}
