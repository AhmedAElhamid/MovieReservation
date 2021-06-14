package sample;

import Database.ConnectionUtils;
import Database.DatabaseUtils;
import Models.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class AddMovieController {
    @FXML
    public TextField categoryText;

    @FXML
    public TextField movieText;

    private DatabaseUtils db;

    @FXML
    public void initialize() {
        Connection connection = new ConnectionUtils().getConnection();
        db = new DatabaseUtils(connection);
    }

    public void handleSwitchToCinemaScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cinema.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 800));
    }

    public void handleAddMovie(ActionEvent actionEvent) {
        String movieName = movieText.getText();
        String category = categoryText.getText();
        if(movieName == null || movieName.length() == 0 || category == null || category.length()==0){
            return;
        }
        Movie movie = new Movie(movieName,category);
        db.AddMovie(movie);
        movieText.clear();
        categoryText.clear();
    }
}
