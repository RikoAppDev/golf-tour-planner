package dev.riko.golftourplanner;

import dev.riko.golftourplanner.controlers.WorldFXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("world.fxml"));
        Parent root = fxmlLoader.load();
        WorldFXMLController worldFXMLController = fxmlLoader.getController();

        Scene scene = new Scene(root, 1024, 720);

        stage.setTitle("Golf Tour Planner");
        stage.setScene(scene);
        stage.getIcons().add(new Image("golf_cart_icon.png"));
        stage.setFullScreen(true);
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
