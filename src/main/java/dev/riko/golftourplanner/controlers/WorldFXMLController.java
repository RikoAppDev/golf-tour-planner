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
import javafx.scene.layout.VBox;
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
    public AnchorPane createTour_panel;
    @FXML
    public Button createTourBtn;
    @FXML
    public RadioButton team;
    @FXML
    public RadioButton solo;
    @FXML
    public TextField firstname;
    @FXML
    public TextField lastname;
    @FXML
    public TextField age;
    @FXML
    public TextField hcp;
    @FXML
    public TextField club;
    public AnchorPane soloTypePanel;
    @FXML
    public Button closeTourBtn;
    @FXML
    public AnchorPane teamTypePanel;
    @FXML
    public VBox golfCourses;
    @FXML
    private Canvas worldMap;

    private int amount;

    public void generatePlaces(Stage stage) {
        swapPanels();
        createTourBtn.setVisible(true);

        try {
            amount = Integer.parseInt(placesAmountInput.getCharacters().toString());
            if (amount < 500 || 5000 < amount) {
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
            if (amount < 500) {
                a.setContentText("The number of places cannot be lower than 100.");
            } else if (amount > 5000) {
                a.setContentText("The number of places cannot be greater than 5000.");
            }
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
        createTour_panel.setVisible(false);
        places_panel.setVisible(true);
        shortestPathList.getItems().clear();
    }

    private void listPlaces(List<String> placeList) {
        placesList.getItems().clear();
        placesList.getItems().addAll(placeList);
    }


    public void highlightPlaceOnMap(List<Place> filteredPlaces) {
        showPlacesOnMap(World.getInstance().getPlaceList());
        if (!searchPlaceInput.getCharacters().toString().equals("")) {
            Place showPlace = filteredPlaces.get(0);
            double x = scaleAxis(showPlace.getLatitude());
            double y = scaleAxis(showPlace.getLongitude());

            GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
            markPlace(graphicsContext, x, y, Color.RED);
        } else {
            showPlacesOnMap(World.getInstance().getPlaceList());
        }
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

    public List<Place> filterPlaces() {
        List<Place> places = new ArrayList<>(World.getInstance().getPlaceList());
        List<String> placeTitles = new ArrayList<>();
        List<Place> filteredPlaces = new ArrayList<>();

        for (Place place : places) {
            if (place.getTitle().toLowerCase().contains(searchPlaceInput.getCharacters().toString().toLowerCase())) {
                placeTitles.add(place.placeInfo());
                filteredPlaces.add(place);
            }
        }

        placesList.getItems().clear();
        placesList.getItems().addAll(placeTitles);
        return filteredPlaces;
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
        return x * 100 / amount * 8.6;
    }

    @FXML
    private void closeTourPanel() {
        createTour_panel.setVisible(false);
    }

    @FXML
    private void openTourPanel() {
        createTour_panel.setVisible(true);
        showPlacesOnMap(World.getInstance().getPlaceList());
        listPlacesWithFacility(FacilityType.GOLF_COURSE);
    }

    @FXML
    private void showSoloForm() {
        teamTypePanel.setVisible(false);
        soloTypePanel.setVisible(true);
    }

    @FXML
    private void showTeamForm() {
        soloTypePanel.setVisible(false);
        teamTypePanel.setVisible(true);
    }

    @FXML
    private void swapDestinations() {
        String startDestination = finalDestinationInput.getText().strip().toLowerCase();
        String sFirst = String.valueOf(startDestination.charAt(0)).toUpperCase();
        String finalDestination = startDestinationInput.getText().strip().toLowerCase();
        String fFirst = String.valueOf(finalDestination.charAt(0)).toUpperCase();

        startDestination = startDestination.substring(1);
        finalDestination = finalDestination.substring(1);

        startDestination = sFirst + startDestination;
        finalDestination = fFirst + finalDestination;
        startDestinationInput.setText(startDestination);
        finalDestinationInput.setText(finalDestination);
    }
}
