package Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class ShowTime {

    private String MovieName;
    private String ShowPeriod;
    private LocalDate ShowDate;

    public ShowTime(String movieName, String showPeriod, LocalDate showDate) {
        MovieName = movieName;
        ShowPeriod = showPeriod;
        ShowDate = showDate;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getShowPeriod() {
        return ShowPeriod;
    }

    public void setShowPeriod(String showPeriod) {
        ShowPeriod = showPeriod;
    }

    public LocalDate getShowDate() {
        return ShowDate;
    }

    public void setShowDate(LocalDate showDate) {
        ShowDate = showDate;
    }
}
