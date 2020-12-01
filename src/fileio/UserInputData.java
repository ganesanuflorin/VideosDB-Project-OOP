package fileio;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private final ArrayList<String> listrat = new ArrayList<>();



    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }




    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

    /**
     * am vazut filmele de la favorite
     * @param username am avut nevoie de user
     * @param input datebase
     * @param title am avut nevoie de titlu
     * @return ce trebuie
     */
    public static String favorite(final String username, final Input input, final String title) {
        UserInputData useraux = null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                useraux = user;
                break;
            }
        }
        assert useraux != null;
        if (useraux.getFavoriteMovies().contains(title)) {
            return "error -> " + title + " is already in favourite list";
        }

        for (Map.Entry<String, Integer> istoric : useraux.getHistory().entrySet()) {
            if (istoric.getKey().equals(title)) {
                useraux.getFavoriteMovies().add(title);
                return "success -> " + title + " was added as favourite";
            }
        }

        for (Map.Entry<String, Integer> istoric : useraux.getHistory().entrySet()) {
            if (!(istoric.getKey().equals(title))) {
                return "error -> " + title + " is not seen";
            }
        }

        return null;
    }

    public static String view(final String username, final Input input, final String title) {
        UserInputData useraux = null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                useraux = user;
                break;
            }
        }

        assert useraux != null;
        for (Map.Entry<String, Integer> istoric : useraux.getHistory().entrySet()) {
            int value = istoric.getValue();
            if (istoric.getKey().equals(title)) {
                value += 1;
                istoric.setValue(value);
                return  "success -> " + title + " was viewed with total views of " + value;
            }
        }
        useraux.getHistory().put(title, 1);
        return  "success -> " + title + " was viewed with total views of " + "1";
    }


    public ArrayList<String> getListrat() {
        return listrat;
    }


    public static String rating(final String username, final Input input, final String title,
                                final double grade, final ActionInputData actionInputData) {
        UserInputData useraux = null;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                useraux = user;
                break;
            }
        }
        for (MovieInputData movieInputData : input.getMovies()) {
            if (movieInputData.getTitle().equals(title)) {
                assert useraux != null;
                if (useraux.getHistory().containsKey(title)) {
                    if (!(useraux.getListrat().contains(title))) {
                        movieInputData.getMovieRating().add(grade);
                        useraux.getListrat().add(title);
                        return "success -> " + title + " was rated with "
                                + grade + " by " + useraux.getUsername();
                    } else {
                        return "error -> " + title + " has been already rated";
                    }
                }
            }
        }
            for (SerialInputData serialInputData : input.getSerials()) {
                if (serialInputData.getTitle().equals(title)) {
                    assert useraux != null;
                    if (useraux.getHistory().containsKey(title)) {
                        if (!(useraux.getListrat().
                                contains(title + actionInputData.getSeasonNumber()))) {
                            serialInputData.getSeasons().get(actionInputData.
                                    getSeasonNumber() - 1).getRatings().add(grade);
                            useraux.getListrat().add(title + actionInputData.getSeasonNumber());
                            return "success -> " + title + " was rated with "
                                    + grade + " by " + useraux.getUsername();
                        } else {
                            return "error -> " + title + " has been already rated";
                        }
                    }
                }
            }
        return "error -> " + title + " is not seen";
    }
}


