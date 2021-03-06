package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cinema.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SummaryReport.fxml"));
        primaryStage.setTitle("Movie Reservation");
        primaryStage.setScene(new Scene(root, 850, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
