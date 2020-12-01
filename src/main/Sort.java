package main;

import java.util.Comparator;

public final class Sort implements Comparator<Compar> {
    public int compare(final Compar a, final Compar b) {
        if (a.getRating() > b.getRating()) {
            return 1;
        } else if (b.getRating() > a.getRating()) {
            return -1;
        } else {
            return a.getName().compareTo(b.getName());
        }
    }
}
