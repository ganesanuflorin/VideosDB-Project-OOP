package main;

public final class Compar {
    private final double rating;
    private final String name;

    public Compar(final double rating, final String name) {
        this.rating = rating;
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }
}
