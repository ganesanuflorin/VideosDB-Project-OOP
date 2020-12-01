package main;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


public class Query {


    public static String average(final Input input, final ActionInputData actionInputData) {
        ArrayList<Compar> listarating = new ArrayList<>();
        for (ActorInputData actorInputData : input.getActors()) {

            double rating = 0;
            int count = 0;
            for (String title : actorInputData.getFilmography()) {
                for (MovieInputData movieInputData : input.getMovies()) {
                    if (title.equals(movieInputData.getTitle())
                            && movieInputData.movieratingAverage() != 0) {

                        rating += movieInputData.movieratingAverage();
                        count++;
                    }
                }
                for (SerialInputData serialInputData : input.getSerials()) {
                    if (title.equals(serialInputData.getTitle())
                            && serialInputData.serialAverage() != 0) {
                        rating += serialInputData.serialAverage();
                        count++;
                    }
                }
            }
            if (rating != 0) {
                rating = rating / count;
                listarating.add(new Compar(rating, actorInputData.getName()));
            }
        }
        Collections.sort(listarating, new Sort());
        for (Compar compar : listarating) {
            System.out.println(compar.getName());
            System.out.println(compar.getRating());
        }
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listarating);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listarating.size()) {
            aux = listarating.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux; i++) {
            queryresultBuilder.append(listarating.get(i).getName()).append(", ");
        }
        queryresult = queryresultBuilder.toString();
        queryresult = queryresult.substring(0, queryresult.length() - 2);
        return queryresult + "]";

    }

    public static String awards(final Input input, final ActionInputData actionInputData) {
        LinkedHashMap<String, Integer> actorAwards = new LinkedHashMap<>();
        for (ActorInputData actorInputData : input.getActors()) {
            int count = 0;
            int var = 3;
            for (String awards : actionInputData.getFilters().get(var)) {
                if (actorInputData.getAwards().containsKey(ActorsAwards.valueOf(awards))) {
                    count++;
                }
            }
            if (count == actionInputData.getFilters().get(var).size()) {
                int actor = 0;
                for (Map.Entry<ActorsAwards, Integer> actorMap
                        : actorInputData.getAwards().entrySet()) {
                    actor += actorMap.getValue();
                }
                actorAwards.put(actorInputData.getName(), actor);
            }
        }
        LinkedHashMap<String, Integer> sortedActorAwards =
                new LinkedHashMap<>();
        actorAwards.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.
                reverseOrder())).forEach(x -> sortedActorAwards.put(x.getKey(), x.getValue()));
        ArrayList<String> listAwards = new ArrayList<>(sortedActorAwards.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listAwards);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listAwards.size()) {
            aux = listAwards.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux; i++) {
            queryresultBuilder.append(listAwards.get(i)).append(", ");
        }
        queryresult = queryresultBuilder.toString();
        return queryresult + "]";
    }

    public static String filterDescription(final Input input,
                                           final ActionInputData actionInputData) {
        ArrayList<String> listFiltDescrip = new ArrayList<>();
        for (ActorInputData actorInputData : input.getActors()) {
            int count = 0;
            for (String description : actionInputData.getFilters().get(2)) {
                if (actorInputData.getCareerDescription().toLowerCase().contains(description)) {
                    count++;
                }
            }
            if (count == actionInputData.getFilters().size()) {
                listFiltDescrip.add(actorInputData.getName());
            }
        }

        Collections.sort(listFiltDescrip);
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listFiltDescrip);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listFiltDescrip.size()) {
            aux = listFiltDescrip.size();
        }
        for (int i = 0; i < aux; i++) {
            queryresult += listFiltDescrip.get(i) + ", ";
        }
        return queryresult + "]";

    }

    public static String queryRating(final Input input, final ActionInputData actionInputData) {
        TreeMap<String, Double> mapQueryRating = new TreeMap<>();
        int year;
        if (actionInputData.getFilters().get(0).get(0) != null) {
            year = Integer.parseInt(actionInputData.getFilters().get(0).get(0));
        } else {
            year = 0;
        }

        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                if ((year == 0 || movieInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || movieInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int count = 0;
                    double ratings = 0;
                    if (movieInputData.movieratingAverage() != ratings) {
                        ratings += movieInputData.movieratingAverage();
                        count++;
                    }
                    if (ratings != 0) {
                        ratings = ratings / count;
                        mapQueryRating.put(movieInputData.getTitle(), ratings);
                    }
                }

            }
        } else if (actionInputData.getObjectType().equals("shows")) {
            for (SerialInputData serialInputData : input.getSerials()) {
                if ((year == 0 || serialInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || serialInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int count = 0;
                    double ratings = 0;
                    if (serialInputData.serialAverage() != ratings) {
                        ratings += serialInputData.serialAverage();
                        count++;
                    }
                    if (ratings != 0) {
                        ratings = ratings / count;
                        mapQueryRating.put(serialInputData.getTitle(), ratings);
                    }
                }

            }
        }

        TreeMap<String, Double> sortedQueryRating = new TreeMap<>();
        mapQueryRating.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                forEach(x -> sortedQueryRating.put(x.getKey(), x.getValue()));
        ArrayList<String> listRating = new ArrayList<>(sortedQueryRating.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listRating);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listRating.size()) {
            aux = listRating.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux - 1; i++) {
            queryresultBuilder.append(listRating.get(i)).append(", ");
        }
        if (aux >= 1) {
            queryresultBuilder.append(listRating.get(aux - 1));
        }
        return queryresult + "]";
    }

    public static String queryFavorite(final Input input, final ActionInputData actionInputData) {
        TreeMap<String, Integer> mapQueryFavorite = new TreeMap<>();
        int year;
        if (actionInputData.getFilters().get(0).get(0) != null) {
            year = Integer.parseInt(actionInputData.getFilters().get(0).get(0));
        } else {
            year = 0;
        }

        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                if ((year == 0 || movieInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || movieInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int count = 0;
                    for (UserInputData userInputData : input.getUsers()) {
                        if (userInputData.getFavoriteMovies().contains(movieInputData.getTitle())) {
                            count++;
                        }
                    }
                    if (count != 0) {
                        mapQueryFavorite.put(movieInputData.getTitle(), count);
                    }
                }
            }
        } else if (actionInputData.getObjectType().equals("shows")) {
            for (SerialInputData serialInputData : input.getSerials()) {
                if ((year == 0 || serialInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || serialInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int count = 0;
                    for (UserInputData userInputData : input.getUsers()) {
                        if (userInputData.getFavoriteMovies().
                                contains(serialInputData.getTitle())) {
                            count++;
                        }
                    }
                    if (count != 0) {
                        mapQueryFavorite.put(serialInputData.getTitle(), count);
                    }
                }
            }
        }

        TreeMap<String, Integer> sortedMapQueryFavorte = new TreeMap<>();
        mapQueryFavorite.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                forEach(x -> sortedMapQueryFavorte.put(x.getKey(), x.getValue()));
        ArrayList<String> listQueryFavorite = new ArrayList<>(sortedMapQueryFavorte.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listQueryFavorite);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listQueryFavorite.size()) {
            aux = listQueryFavorite.size();
        }
        for (int i = 0; i < aux - 1; i++) {
            queryresult += listQueryFavorite.get(i) + ", ";
        }
        if (aux >= 1) {
            queryresult += listQueryFavorite.get(aux - 1);
        }
        return queryresult + "]";
    }

    public static String queryDuration(final Input input, final ActionInputData actionInputData) {
        TreeMap<String, Integer> mapQueryDuration = new TreeMap<>();
        int year;
        if (actionInputData.getFilters().get(0).get(0) != null) {
            year = Integer.parseInt(actionInputData.getFilters().get(0).get(0));
        } else {
            year = 0;
        }

        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                if ((year == 0 || movieInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || movieInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int duration = movieInputData.getDuration();
                    if (duration != 0) {
                        mapQueryDuration.put(movieInputData.getTitle(), duration);
                    }
                }
            }
        } else if (actionInputData.getObjectType().equals("shows")) {
            for (SerialInputData serialInputData : input.getSerials()) {
                if ((year == 0 || serialInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || serialInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    int duration = serialInputData.serialSum();
                    if (duration != 0) {
                        mapQueryDuration.put(serialInputData.getTitle(), duration);
                    }
                }

            }
        }

        TreeMap<String, Integer> sortedQueryDuration = new TreeMap<>();
        mapQueryDuration.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                forEach(x -> sortedQueryDuration.put(x.getKey(), x.getValue()));
        ArrayList<String> listDuration = new ArrayList<>(sortedQueryDuration.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listDuration);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listDuration.size()) {
            aux = listDuration.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux - 1; i++) {
            queryresultBuilder.append(listDuration.get(i)).append(", ");
        }
        if (aux >= 1) {
            queryresultBuilder.append(listDuration.get(aux - 1));
        }
        return queryresultBuilder.append("]").toString();
    }

    public static String queryMostViewed(final Input input, final ActionInputData actionInputData) {
        TreeMap<String, Integer> mapQueryMostView = new TreeMap<>();
        int year;
        if (actionInputData.getFilters().get(0).get(0) != null) {
            year = Integer.parseInt(actionInputData.getFilters().get(0).get(0));
        } else {
            year = 0;
        }

        if (actionInputData.getObjectType().equals("movies")) {
            for (MovieInputData movieInputData : input.getMovies()) {
                int view = 0;
                if ((year == 0 || movieInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || movieInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    for (UserInputData userInputData : input.getUsers()) {
                        if (userInputData.getHistory().containsKey(movieInputData.getTitle())) {
                            view += userInputData.getHistory().get(movieInputData.getTitle());
                        }
                    }
                }
                if (view != 0) {
                    mapQueryMostView.put(movieInputData.getTitle(), view);
                }

            }
        } else if (actionInputData.getObjectType().equals("shows")) {
            for (SerialInputData serialInputData : input.getSerials()) {
                int view = 0;
                if ((year == 0 || serialInputData.getYear() == year)
                        && (actionInputData.getFilters().get(1).get(0) == null
                        || serialInputData.getGenres().
                        contains(actionInputData.getFilters().get(1).get(0)))) {
                    for (UserInputData userInputData : input.getUsers()) {
                        if (userInputData.getHistory().containsKey(serialInputData.getTitle())) {
                            view += userInputData.getHistory().get(serialInputData.getTitle());
                        }
                    }
                }
                if (view != 0) {
                    mapQueryMostView.put(serialInputData.getTitle(), view);
                }
            }
        }

        TreeMap<String, Integer> sortedQueryMostView = new TreeMap<>();
        mapQueryMostView.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                forEach(x -> sortedQueryMostView.put(x.getKey(), x.getValue()));
        ArrayList<String> listMostView = new ArrayList<>(sortedQueryMostView.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listMostView);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listMostView.size()) {
            aux = listMostView.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux - 1; i++) {
            queryresultBuilder.append(listMostView.get(i));
            queryresultBuilder.append(", ");
        }
        if (aux >= 1) {
            queryresultBuilder.append(listMostView.get(aux - 1));
        }
        return queryresultBuilder.append("]").toString();
    }

    /**
     * nu inteleg de ce nu mi a ordonat mapa in aceasta functie,
     * mai ales ca am folosit peste tot aceeasi metoda
     * @param input datebase
     * @param actionInputData datebse
     * @return ce trebuia
     */
    public static String users(final Input input, final ActionInputData actionInputData) {
        TreeMap<String, Integer> mapUsers = new TreeMap<>();
        for (UserInputData userInputData : input.getUsers()) {
            int countRating = userInputData.getListrat().size();
            if (countRating != 0) {
                mapUsers.put(userInputData.getUsername(), countRating);
            }
        }

        TreeMap<String, Integer> sortedUser = new TreeMap<>();
        mapUsers.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()).
                forEach(x -> sortedUser.put(x.getKey(), x.getValue()));
        ArrayList<String> listUsers = new ArrayList<>(sortedUser.keySet());

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(listUsers);
        }
        String queryresult;
        queryresult = "Query result: [";
        int aux = actionInputData.getNumber();
        if (aux > listUsers.size()) {
            aux = listUsers.size();
        }
        StringBuilder queryresultBuilder = new StringBuilder(queryresult);
        for (int i = 0; i < aux - 1; i++) {
            queryresultBuilder.append(listUsers.get(i)).append(", ");
        }
        if (aux >= 1) {
            queryresultBuilder.append(listUsers.get(aux - 1));
        }
        return queryresultBuilder.append("]").toString();
    }

}
