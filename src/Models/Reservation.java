package Models;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    private String[] chairsReserved;

    private String movieName;

    private LocalDate date;

    private String period;


    public Reservation(String[] chairsReserved, String movieName, LocalDate date, String period) {
        this.chairsReserved = chairsReserved;
        this.movieName = movieName;
        this.date = date;
        this.period = period;
    }

    public String[] getChairsReserved() {
        return chairsReserved;
    }

    public void setChairReserved(String[] chairsReserved) {
        this.chairsReserved = chairsReserved;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
