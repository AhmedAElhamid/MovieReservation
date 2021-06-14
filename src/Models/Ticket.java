package Models;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Date;

public class Ticket {

    private String movieName;
    private String period;
    private LocalDate date;
    private String seatName;

    public Ticket(String movieName, String period, LocalDate date, String seatName) {
        this.movieName = movieName;
        this.period = period;
        this.date = date;
        this.seatName = seatName;
    }

    public String getMovieName() {
        return movieName;
    }

    private void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPeriod() {
        return period;
    }

    private void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSeatName() {
        return seatName;
    }

    private void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getTicket(){
        Object[] values = new String[]{movieName,period,date.toString(),seatName};
        return MessageFormat.format( "Movie : {0} \nPeriod : {1} \nDate : {2} \nSeat : {3} \n",values);
    }
}
