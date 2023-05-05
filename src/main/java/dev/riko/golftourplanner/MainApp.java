package dev.riko.golftourplanner;

import dev.riko.golftourplanner.controlers.WorldFXMLController;
import dev.riko.golftourplanner.pathfinding.SearchOptimalTrip;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("world.fxml"));
        Parent root = fxmlLoader.load();
        WorldFXMLController worldFXMLController = fxmlLoader.getController();

        World world = World.getInstance();

        worldFXMLController.navigateBtn.setOnAction(event -> {
            worldFXMLController.places_panel.setVisible(false);
            worldFXMLController.pathfindingInfo_panel.setVisible(true);
            worldFXMLController.showPlacesOnMap(world.getPlaceList());

            String startDestination = String.valueOf(worldFXMLController.startDestinationInput.getCharacters()).strip();
            String finalDestination = String.valueOf(worldFXMLController.finalDestinationInput.getCharacters()).strip();

            Place startPlace;
            Place finalPlace;

            List<Place> startPlaces = world.getPlaces(startDestination);
            startPlace = startPlaces.get(0);
            List<Place> finalPlaces = world.getPlaces(finalDestination);
            finalPlace = finalPlaces.get(0);

            SearchOptimalTrip optimalTrip = new SearchOptimalTrip(world, startPlace, finalPlace);
            worldFXMLController.airDistanceLabel.setText("Air distance from " + startPlace.getTitle() + " to " + finalPlace.getTitle() + " is " + String.format("%.2f", optimalTrip.getAirDistanceLength()) + "km.");
            worldFXMLController.routeLength.setText("Length of the route: " + String.format("%.2f", optimalTrip.getShortestPathLength()) + "km");

            List<String> routePlaces = new ArrayList<>();

            for (Place place : optimalTrip.getShortestPath()) {
                routePlaces.add(place.getTitle());
                routePlaces.add("⬇️");
            }
            routePlaces = routePlaces.subList(0, routePlaces.size() - 1);
            worldFXMLController.shortestPathList.getItems().clear();
            worldFXMLController.shortestPathList.getItems().addAll(routePlaces);

            worldFXMLController.showShortestPathOnMap(optimalTrip.getShortestPath());
        });

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
