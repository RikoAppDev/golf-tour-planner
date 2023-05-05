package dev.riko.golftourplanner.controlers;

import dev.riko.golftourplanner.exeptions.NonAllowedInputException;
import dev.riko.golftourplanner.utils.GenerateData;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WorldFXMLController {
    @FXML
    public TextField searchPlaceInput;
    @FXML
    public Button searchPlaceBtn;
    @FXML
    public Button generatePlacesBtn;
    @FXML
    public TextField placesAmountInput;
    @FXML
    public Label infoLabel;
    @FXML
    public ListView<String> placesList;
    @FXML
    public TextField startDestinationInput;
    @FXML
    public TextField finalDestinationInput;
    @FXML
    public Button navigateBtn;
    @FXML
    public ListView<String> shortestPathList;
    @FXML
    public Label airDistanceLabel;
    @FXML
    public Label routeLength;
    @FXML
    public Label shortestRouteLabel;
    @FXML
    public AnchorPane pathfindingInfo_panel;
    @FXML
    public AnchorPane places_panel;
    @FXML
    public Button closePathfindingInfoBtn;
    @FXML
    public Button searchGolfCoursePlaces;
    @FXML
    private Canvas worldMap;

    public void generatePlaces(Stage stage) {
        swapPanels();

        try {
            int amount = Integer.parseInt(placesAmountInput.getCharacters().toString());
            if (amount < 100) {
                throw new NonAllowedInputException();
            }
            GenerateData generateData = new GenerateData(amount);

            World world = World.getInstance();
            world.setPlaceList(generateData.getData());
            showPlacesOnMap(world.getPlaceList());
            List<String> placeTitles = new ArrayList<>();
            world.getPlaceList().forEach(place -> placeTitles.add(place.placeInfo()));
            listPlaces(placeTitles);
            infoLabel.setVisible(false);
        } catch (NonAllowedInputException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.initOwner(stage);
            a.setTitle("Error");
            a.setContentText("The number of places cannot be lower than 100.");
            a.showAndWait();
        } catch (NumberFormatException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.initOwner(stage);
            a.setTitle("Error");
            a.setContentText("This input field takes only numbers.");
            a.showAndWait();
        }
    }

    public void showPlacesOnMap(List<Place> placeList) {
        GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, worldMap.getWidth(), worldMap.getHeight());

        worldMap.setHeight(874);
        worldMap.setWidth(874);
        placeList.forEach(place -> {
            double x = scaleAxis(place.getLatitude());
            double y = scaleAxis(place.getLongitude());

            markPlace(graphicsContext, x, y, Color.BLACK);
            graphicsContext.setStroke(Color.GRAY);

            place.getPlaceConnections().forEach(connection -> {
                double x1 = scaleAxis(connection.getLatitude());
                double y1 = scaleAxis(connection.getLongitude());

                graphicsContext.strokeLine(x + 7, y + 7, x1 + 7, y1 + 7);
            });
        });
    }

    @FXML
    private void closePathfindingInfo() {
        swapPanels();
        showPlacesOnMap(World.getInstance().getPlaceList());
    }

    public void swapPanels() {
        startDestinationInput.setText("");
        finalDestinationInput.setText("");
        pathfindingInfo_panel.setVisible(false);
        places_panel.setVisible(true);
        shortestPathList.getItems().clear();
    }

    private void listPlaces(List<String> placeList) {
        placesList.getItems().clear();
        placesList.getItems().addAll(placeList);
    }

    public void listPlacesWithFacility(FacilityType facilityType) {
        List<Place> facilityPlaces = World.getInstance().getPlacesWithFacility(facilityType);

        List<String> fpTitles = new ArrayList<>();

        facilityPlaces.forEach(place -> fpTitles.add(place.placeInfo()));
        listPlaces(fpTitles);

        facilityPlaces.forEach(place -> {
            double x = scaleAxis(place.getLatitude());
            double y = scaleAxis(place.getLongitude());

            GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
            markPlace(graphicsContext, x, y, Color.GREEN);
        });
    }

    public void filterPlaces() {
        List<Place> places = new ArrayList<>(World.getInstance().getPlaceList());
        List<String> placeTitles = new ArrayList<>();

        for (Place place : places) {
            if (place.getTitle().toLowerCase().contains(searchPlaceInput.getCharacters().toString().toLowerCase())) {
                placeTitles.add(place.placeInfo());
            }
        }

        placesList.getItems().clear();
        placesList.getItems().addAll(placeTitles);
    }

    public void showShortestPathOnMap(List<Place> shortestPath) {
        GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();

        Place place = shortestPath.get(0);

        double x = scaleAxis(place.getLatitude());
        double y = scaleAxis(place.getLongitude());
        markPlace(graphicsContext, x, y, Color.RED);

        graphicsContext.setStroke(Color.BLUE);

        for (int i = 1; i < shortestPath.size() - 1; i++) {
            place = shortestPath.get(i);

            x = scaleAxis(place.getLatitude());
            y = scaleAxis(place.getLongitude());
            markPlace(graphicsContext, x, y, Color.BLUE);

            double x1 = scaleAxis(shortestPath.get(i - 1).getLatitude());
            double y1 = scaleAxis(shortestPath.get(i - 1).getLongitude());

            graphicsContext.strokeLine(x + 7, y + 7, x1 + 7, y1 + 7);
        }
        place = shortestPath.get(shortestPath.size() - 1);

        x = scaleAxis(place.getLatitude());
        y = scaleAxis(place.getLongitude());
        markPlace(graphicsContext, x, y, Color.RED);

        double x1 = scaleAxis(shortestPath.get(shortestPath.size() - 2).getLatitude());
        double y1 = scaleAxis(shortestPath.get(shortestPath.size() - 2).getLongitude());
        graphicsContext.strokeLine(x + 7, y + 7, x1 + 7, y1 + 7);
    }

    private void markPlace(GraphicsContext graphicsContext, double x, double y, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x, y, 14, 14);
    }

    private double scaleAxis(double x) {
        return x * 8.6;
    }
}
