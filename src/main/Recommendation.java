package main;

import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Recommendation {
    /**
     * aici fac taskul standard de la recomandari ale unui user
     * @param input datebase
     * @param username am avut nevoie de un user
     * @return ce trebuia
     */
    public static String recomStandard(final Input  input, final String username) {
        UserInputData useraux = null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                useraux = user;
                break;
            }
        }
        for (MovieInputData movieInputData : input.getMovies()) {
            assert useraux != null;
            if (!(useraux.getHistory().containsKey(movieInputData.getTitle()))) {
                return "StandardRecommendation result: " + movieInputData.getTitle();
            }
        }
        for (SerialInputData serialInputData : input.getSerials()) {
            assert useraux != null;
            if (!(useraux.getHistory().containsKey(serialInputData.getTitle()))) {
                return "StandardRecommendation result: " + serialInputData.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * aici am afisat cel mai bun film nevazut de un user
     * @param input datebase
     * @param username am avut nevoie de un user
     * @return ce trebuia
     */
    public static String recomBestUnseen(final Input input, final String username) {
        LinkedHashMap<String, Integer> mapUnSeen = new LinkedHashMap<>();
        UserInputData useraux = null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                useraux = user;
                break;
            }
        }
        for (MovieInputData movieInputData : input.getMovies()) {
            int view = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (userInputData.getHistory().containsKey(movieInputData.getTitle())) {
                    view += userInputData.getHistory().get(movieInputData.getTitle());
                }
                assert useraux != null;
                if (!(useraux.getHistory().containsKey(movieInputData.getTitle()))) {
                        mapUnSeen.put(movieInputData.getTitle(), view);
                }
            }
        }
        for (SerialInputData serialInputData : input.getSerials()) {
            int view = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (userInputData.getHistory().containsKey(serialInputData.getTitle())) {
                    view += userInputData.getHistory().get(serialInputData.getTitle());
                }
                assert useraux != null;
                if (!(useraux.getHistory().containsKey(serialInputData.getTitle()))) {
                        mapUnSeen.put(serialInputData.getTitle(), view);
                }
            }
        }
        LinkedHashMap<String, Integer> sortedmapUnSeen = new LinkedHashMap<>();
        mapUnSeen.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                forEach(x -> sortedmapUnSeen.put(x.getKey(), x.getValue()));
        ArrayList<String> listUnSeen = new ArrayList<>(sortedmapUnSeen.keySet());
        String result;
        result = "BestRatedUnseenRecommendation result: ";

        if (listUnSeen.size() == 0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        result += listUnSeen.get(0);

        return result;
    }
}
