package sample;

import Database.ConnectionUtils;
import Database.DatabaseUtils;
import Models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class ReportController {

    @FXML
    private PieChart CrowdedTimingsPieChart;

    @FXML
    private BarChart<String,Integer> ChairsPerMovieBarChart;

    private ObservableList<PieChart.Data> pieChartData;

    private DatabaseUtils db;

    private final String[] Periods = new String[]{
            "01:00 PM",
            "03:00 PM",
            "05:00 PM",
            "07:00 PM",
            "09:00 PM",
            "11:00 PM",
            "01:00 AM",
    };

    @FXML
    public void initialize() {
        Connection connection = new ConnectionUtils().getConnection();
        db = new DatabaseUtils(connection);
        initializeSoldChairsChart();
        initializeCrowdedTimingsChart();
    }

    private void initializeCrowdedTimingsChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (String period:
             Periods) {
            pieChartData.add(new PieChart.Data(period,db.GetPeriodReservationsCount(period)));
        }
        CrowdedTimingsPieChart.getData().addAll(pieChartData);
    }

    private void initializeSoldChairsChart() {
        ArrayList<Movie> Movies = db.GetMovies();
        XYChart.Series series = new XYChart.Series();
        for (Movie movie:
             Movies) {
            series.getData().add(new XYChart.Data(movie.getMovieName(),db.GetNumberOfSoldSeatsPerMovie(movie.getMovieName())));
        }
        ChairsPerMovieBarChart.getData().add(series);
    }

    public void handleSwitchToCinemaScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cinema.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 800));
    }
}
