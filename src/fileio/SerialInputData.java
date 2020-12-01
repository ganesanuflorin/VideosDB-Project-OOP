package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;


    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
    public static SerialInputData getSerial(final Input input, final String title) {
        for (SerialInputData getserial : input.getSerials()) {
            if (getserial.getTitle().equals(title)) {
                return getserial;
            }
        }
        return null;

    }
    public double serialAverage() {
        double aux = 0;
        int count = 0;
        for (int i = 0; i < this.seasons.size(); i++) {
            aux += this.seasons.get(i).resultAverage();
            count++;
        }
        if (aux != 0) {
            aux = aux / count;
        }
        return aux;
    }

    public int serialSum() {
        int sum = 0;
        for (int i  = 0; i < this.seasons.size(); i++) {
            sum += this.seasons.get(i).getDuration();
        }
        return sum;
    }

}
