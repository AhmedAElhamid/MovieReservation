package Models;

import java.util.ArrayList;

public class Movie {

    private String MovieName;

    private String Category;

    private final String[] ShowPeriods = new String[]{
            "01:00 PM",
            "03:00 PM",
            "05:00 PM",
            "07:00 PM",
            "09:00 PM",
            "11:00 PM",
            "01:00 AM",
    };

    public Movie(String movieName, String category) {
        MovieName = movieName;
        Category = category;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String[] getShowPeriods() {
        return ShowPeriods;
    }
}
