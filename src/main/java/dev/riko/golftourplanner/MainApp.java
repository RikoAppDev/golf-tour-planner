package dev.riko.golftourplanner;

import dev.riko.golftourplanner.controlers.WorldFXMLController;
import dev.riko.golftourplanner.exeptions.MissingDestinationException;
import dev.riko.golftourplanner.exeptions.NoPathFound;
import dev.riko.golftourplanner.exeptions.UnknownPlaceException;
import dev.riko.golftourplanner.pathfinding.SearchOptimalTrip;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        Platform.runLater(root::requestFocus);
        WorldFXMLController worldFXMLController = fxmlLoader.getController();

        World world = World.getInstance();

        worldFXMLController.generatePlacesBtn.setOnAction(event -> worldFXMLController.generatePlaces(stage));
        worldFXMLController.placesAmountInput.setOnAction(event -> worldFXMLController.generatePlaces(stage));

        worldFXMLController.navigateBtn.setOnAction(event -> {
            try {
                worldFXMLController.showPlacesOnMap(world.getPlaceList());

                String startDestination = String.valueOf(worldFXMLController.startDestinationInput.getCharacters()).strip();
                String finalDestination = String.valueOf(worldFXMLController.finalDestinationInput.getCharacters()).strip();
                if (startDestination.equals("") || finalDestination.equals("")) {
                    throw new MissingDestinationException();
                } else if (!world.searchCity(startDestination)) {
                    throw new UnknownPlaceException(startDestination);
                } else if (!world.searchCity(finalDestination)) {
                    throw new UnknownPlaceException(finalDestination);
                }

                worldFXMLController.places_panel.setVisible(false);
                worldFXMLController.pathfindingInfo_panel.setVisible(true);

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
            } catch (MissingDestinationException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Route cannot be calculated because of missing destination.");
                a.showAndWait();
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.initOwner(stage);
                a.setTitle("Error");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            } catch (UnknownPlaceException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("City " + e.getMessage() + " does not exist.");
                a.showAndWait();
            } catch (NoPathFound e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.initOwner(stage);
                a.setTitle("Error");
                a.setContentText("Shortest path cannot be found, try regenerate the map.");
                a.showAndWait();
            }
        });

        worldFXMLController.searchPlaceBtn.setOnAction(event -> {
            try {
                worldFXMLController.filterPlaces();
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            }
        });
        worldFXMLController.searchPlaceInput.setOnKeyTyped(event -> {
            try {
                worldFXMLController.filterPlaces();
            } catch (NullPointerException e) {
                worldFXMLController.searchPlaceInput.setText("");
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            }
        });

        worldFXMLController.searchGolfCoursePlaces.setOnAction(event -> {
            try {
                worldFXMLController.listPlacesWithFacility(FacilityType.GOLF_COURSE);
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            }
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
