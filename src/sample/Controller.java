package sample;


import Database.ConnectionUtils;
import Database.DatabaseUtils;
import Models.Movie;
import Models.Reservation;
import Models.ShowTime;
import Models.Ticket;
import Utils.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {

    private final char[] SeatsCharacters = new char[]{
            'A','B','C','D','E','F','G','Z','H','I','J','K','L','M','N'
    };

    private ArrayList<String> SeatsReserved;

    private ArrayList<String> SeatsSelected;

//    private final String[] Movies = new String[]{
//            "Million Dollar Baby",
//            "Stand by Me",
//            "Streets of Fire",
//    };

    private final String[] Periods = new String[]{
        "01:00 PM",
        "03:00 PM",
        "05:00 PM",
        "07:00 PM",
        "09:00 PM",
        "11:00 PM",
        "01:00 AM",
    };

    private DatabaseUtils db;

    @FXML
    private GridPane seats;

    @FXML
    private ChoiceBox<String> movies;

    @FXML
    private ChoiceBox<String> categories;

    @FXML
    private ChoiceBox<String> periods;

    @FXML
    private DatePicker date;

    @FXML
    private Text dateError;

    @FXML
    private Text movieError;

    @FXML
    private Text periodError;

    @FXML
    private Text seatError;

    @FXML
    public void initialize() {
        Connection connection = new ConnectionUtils().getConnection();
        db = new DatabaseUtils(connection);
        SeatsReserved = new ArrayList<>();
        SeatsSelected = new ArrayList<>();
        initializeCategories();
        initializeSeats();
        initializeMovies();
        initializePeriods();
        initializeDatePicker();
    }

    private void initializeCategories() {
        for (String category:
                db.GetCategories()) {
            categories.getItems().add(category);
        }
        categories.setOnAction(actionEvent -> {
            if(categories.getValue().equals("All Movies") || categories.getValue() == null || categories.getValue().length() == 0){
                initializeMovies();
            }else{
                filterMovies();
            }
        });
    }

    private void filterMovies() {
        ArrayList<Movie> moviesFiltered = db.GetMoviesByCategory(categories.getValue());
        movies.getItems().clear();
        for (Movie movie:
                moviesFiltered) {
            movies.getItems().add(movie.getMovieName());
        }
    }

    private void initializePeriods() {
        for (String period: Periods
             ) {
            periods.getItems().add(period);
        }
        periods.setOnAction(actionEvent -> {
            checkReservation();
        });
    }

    private void initializeMovies() {
        movies.getItems().clear();
        ArrayList<Movie> Movies = db.GetMovies();
        for (Movie movie:
             Movies) {
            movies.getItems().add(movie.getMovieName());
        }
        movies.setOnAction(actionEvent -> {
            checkReservation();
        });
    }
    private void initializeDatePicker() {
        date.setOnAction(actionEvent -> {
            checkReservation();
        });
    }

    private void initializeSeats() {
        for (Node seat: seats.getChildren()) {
            if(seat instanceof Button){
                if(SeatsReserved.contains(getSeatName(seat))){
                    seat.getStyleClass().add("reserved");
                }else{
                    seat.getStyleClass().add("available");
                }
                addEventHandlerToButton((Button) seat);
            }
        }
    }

    private void addEventHandlerToButton(Button button){
        button.setOnAction(actionEvent -> {
            if(button.getStyleClass().contains("available")){
                button.getStyleClass().remove("available");
                button.getStyleClass().add("selecting");
                SeatsSelected.add(getSeatName(button));
            }
            else if(button.getStyleClass().contains("selecting")){
                button.getStyleClass().remove("selecting");
                button.getStyleClass().add("available");
                SeatsSelected.remove(getSeatName(button));
            }
        });
    }


    public void HandleConfirm(ActionEvent actionEvent) {
        if(printTicket() == 0){
            return;
        }else{
            addSeatsToDatabase();
            for (Node seat: seats.getChildren()) {
                if(SeatsSelected.contains(getSeatName(seat))){
                    SeatsReserved.add(getSeatName(seat));
                    seat.getStyleClass().remove("selecting");
                    seat.getStyleClass().add("reserved");
                }
            }
            SeatsSelected.clear();
        }
    }

    private int printTicket() {
        String movieName = movies.getValue();
        String period = periods.getValue();
        LocalDate dateSelected = date.getValue();
        LocalDate now = LocalDate.now();
        if(movieName == null || movieName.length() == 0)
        {
            movieError.setText("Please Select the movie");
            return 0;
        }else{
            movieError.setText("");
        }
        if(dateSelected == null || dateSelected.isBefore(now))
        {
            dateError.setText("Please Select a valid date");
            return 0;
        }else{
            dateError.setText("");
        }
        if(period == null || period.length() == 0)
        {
            periodError.setText("Please Select the period");
            return 0;
        }else{
            periodError.setText("");
        }
        if(SeatsSelected.isEmpty()){
            seatError.setText("Please select the seats to reserve");
            return 0;
        }else{
            seatError.setText("");
            for (String seat:
                 SeatsSelected) {
                Ticket ticket = new Ticket(movieName,period,dateSelected,seat);
                System.out.println(ticket.getTicket());
                FileUtils.writeToFile(ticket.getTicket(),seat,"tickets");
            }
        }
        return 1;
    }

    public void handleClear(ActionEvent actionEvent) {
        if(SeatsSelected.isEmpty()) return;
        for (Node seat: seats.getChildren()) {
            if(SeatsSelected.contains(getSeatName(seat))){
                seat.getStyleClass().remove("selecting");
                seat.getStyleClass().add("available");
            }
        }
        SeatsSelected.clear();
    }

    public void checkReservation(){
        String movie = movies.getValue();
        String period = periods.getValue();
        LocalDate selectedDate = date.getValue();
        if(movie == null || movie.length() == 0 || period == null || period.length()==0 || selectedDate == null){
            return;
        }
        ShowTime showTime = new ShowTime(movie,period,selectedDate);
        SeatsReserved = db.GetReservations(showTime);
        if(!SeatsReserved.isEmpty()){
            setAllChairs();
        }else{
            clearAllChairs();
        }
    }

    private void setAllChairs(){
        for (Node seat:
                seats.getChildren()) {
            if(SeatsReserved.contains(getSeatName(seat))){
                removeStyleClasses(seat);
                seat.getStyleClass().add("reserved");
            }else{
                removeStyleClasses(seat);
                seat.getStyleClass().add("available");
            }
        }
    }

    private void clearAllChairs() {
        for (Node seat :
                seats.getChildren()) {
                removeStyleClasses(seat);
                seat.getStyleClass().add("available");
            }
        SeatsSelected.clear();
    }

    private void addSeatsToDatabase() {
        String[] chairs = SeatsSelected.toArray(new String[0]);
        Reservation reservation = new Reservation(chairs,movies.getValue(), date.getValue(),periods.getValue());
        db.AddReservation(reservation);
    }

    private String getSeatName(Node seat) {
        int row = GridPane.getColumnIndex(seat) == null ? 0 : GridPane.getColumnIndex(seat);
        int column = GridPane.getRowIndex(seat) == null ? 0 : GridPane.getRowIndex(seat);
        return SeatsCharacters[row] + "" + column;
    }

//    public void handleCheck(ActionEvent actionEvent) {
//        checkReservation();
//    }
    public void removeStyleClasses(Node seat){
        seat.getStyleClass().remove("available");
        seat.getStyleClass().remove("reserved");
        seat.getStyleClass().remove("selecting");
    }

    public void handleSwitchToChartScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SummaryReport.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 800));
    }

    public void handleSwitchToAddMovieScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddMovie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 800));
    }
}
