package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    public  ArrayList<Double> getMovieRating() {
        return movieRating;
    }

    private final ArrayList<Double> movieRating = new ArrayList<Double>();


    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }

    /**
     *
     * @return
     */
    public double movieratingAverage() {
        double aux = 0;
        int count = 0;
        for (int i = 0; i < this.movieRating.size(); i++) {
            aux += this.movieRating.get(i);
            count++;
        }
        if (aux != 0) {
            aux = aux / count;
        }
        return aux;
    }
}
