package Database;

import Models.Movie;
import Models.Reservation;
import Models.ShowTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtils {
    private final Connection connection;

    public DatabaseUtils(Connection connection) {
        this.connection = connection;
    }

    public void AddMovie(Movie movie){
        try {
            Statement statement=connection.createStatement();
            for (String period:
                 movie.getShowPeriods()) {
                System.out.println("INSERT INTO `Movies` (`MovieName`,`Category`,`ShowPeriod`) " +
                                "VALUES ('"+  movie.getMovieName() + "', '"+  movie.getCategory() + "','"+  period + "');");
                String sql="INSERT INTO `Movies` (`MovieName`,`Category`,`ShowPeriod`) " +
                        "VALUES ('"+  movie.getMovieName() + "', '"+  movie.getCategory() + "','"+  period + "');";

                if(statement.executeUpdate(sql) != 1){
                    System.out.println("DatabaseUtils.AddMovie" + "current sql statement not executed");
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Movie> GetMovies(){
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            Statement statement=connection.createStatement();
            String sql="SELECT DISTINCT MovieName, Category FROM `Movies`;";
            ResultSet resultSet=statement.executeQuery(sql);
            String movieName;
            String movieCategory;
            while (resultSet.next()){
                movieName = resultSet.getString("MovieName");
                movieCategory = resultSet.getString("Category");
                movies.add(new Movie(movieName,movieCategory));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    public ArrayList<String> GetReservations(ShowTime showTime){
        ArrayList<String> chairsReserved = new ArrayList<>();
        try {
            Statement statement=connection.createStatement();
            String sql="SELECT * FROM `ShowTime` WHERE `MovieName`='"+showTime.getMovieName()+"' " +
                    "AND `ShowDate` = '"+showTime.getShowDate()+"' " +
                    "AND `ShowPeriod` = '"+showTime.getShowPeriod()+"';";
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                chairsReserved.add(resultSet.getString("ChairReserved"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chairsReserved;
    }

    public void AddReservation(Reservation reservation){
        try {
            Statement statement=connection.createStatement();
            for (String chair:
                 reservation.getChairsReserved()) {
                System.out.println("INSERT INTO `ShowTime`" +
                        "(`MovieName`,`ShowDate`,`ShowPeriod`,`ChairReserved`) " +
                        "VALUES ('"+ reservation.getMovieName() + "','"+ reservation.getDate().toString() +"','" +
                        reservation.getPeriod() +"','"+ chair +"');");

                String sql="INSERT INTO `ShowTime`" +
                        "(`MovieName`,`ShowDate`,`ShowPeriod`,`ChairReserved`) " +
                        "VALUES ('"+ reservation.getMovieName() + "','"+ reservation.getDate().toString() +"','" +
                        reservation.getPeriod() +"','"+ chair +"');";

                if(statement.executeUpdate(sql) != 1){
                    System.out.println("DatabaseUtils.AddMovie" + "current sql statement not executed");
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
