package dev.riko.golftourplanner.controlers;

import dev.riko.golftourplanner.MainApp;
import dev.riko.golftourplanner.exeptions.NonAllowedInputException;
import dev.riko.golftourplanner.utils.GenerateData;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code WorldFXMLController} class is the controller class for the WorldFXML.fxml file. This class is responsible for managing user input and updating the graphical user interface based on that input.
 * <p>
 * The class contains methods for generating places, showing places on a map, swapping panels, highlighting places on a map, listing places with a facility, filtering places, and showing the shortest path on a map.
 * <p>
 * Additionally, the class contains a nested NonAllowedInputException class that is thrown when the user inputs an invalid number of places.
 */
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
    public ListView<MainApp.GolfCourseListViewItem> golfCoursesList;
    @FXML
    public Button generateTourBtn;
    @FXML
    public TextField budgetField;
    @FXML
    public ToggleGroup tourType;
    @FXML
    public TextField startPlace;
    @FXML
    public TextField finalPlace;
    @FXML
    public TextField teamSize;
    @FXML
    public TextField teamName;
    @FXML
    private Canvas worldMap;
    /**
     * An integer representing the number of places to generate.
     */
    private int amount;

    /**
     * Generates a specified number of places, sets the generated places in the {@link World} class, and updates the graphical user interface to show the generated places on a map, list the generated places, and hide the info label.
     * <p>
     * If the user inputs an invalid number of places, an error message is displayed on the graphical user interface.
     *
     * @param stage The Stage object representing the graphical user interface window.
     */
    public void generatePlaces(Stage stage) {
        Thread thread = new Thread(() -> {
            try {
                amount = Integer.parseInt(placesAmountInput.getCharacters().toString());
                if (amount < 500 || 5000 < amount) {
                    throw new NonAllowedInputException();
                }
                GenerateData generateData = new GenerateData(amount);

                World world = World.getInstance();
                world.setPlaceList(generateData.getData());

                List<String> placeTitles = new ArrayList<>();
                world.getPlaceList().forEach(place -> placeTitles.add(place.placeInfo()));
                Collections.sort(placeTitles);

                Platform.runLater(() -> {
                    swapPanels();
                    createTourBtn.setVisible(true);
                    showPlacesOnMap(world.getPlaceList());
                    listPlaces(placeTitles);
                    infoLabel.setVisible(false);
                });
            } catch (NonAllowedInputException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(AlertType.ERROR);
                    a.initOwner(stage);
                    a.setTitle("Error");
                    if (amount < 500) {
                        a.setContentText("The number of places cannot be lower than 100.");
                    } else if (amount > 5000) {
                        a.setContentText("The number of places cannot be greater than 5000.");
                    }
                    a.showAndWait();
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(AlertType.ERROR);
                    a.initOwner(stage);
                    a.setTitle("Error");
                    a.setContentText("This input field takes only numbers.");
                    a.showAndWait();
                });
            }
        });
        thread.start();
    }

    /**
     * Shows a list of places on a map with connections between the places.
     *
     * @param placeList The list of places to show on the map.
     */
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

    /**
     * Closes the pathfinding info panel and displays the places panel.
     * Also shows all the places on the map.
     */
    @FXML
    private void closePathfindingInfo() {
        swapPanels();
        showPlacesOnMap(World.getInstance().getPlaceList());
    }

    /**
     * Swaps the visibility of the panels to display the places panel
     * and clear the input fields and list of shortest paths.
     */
    public void swapPanels() {
        startDestinationInput.setText("");
        finalDestinationInput.setText("");
        pathfindingInfo_panel.setVisible(false);
        createTour_panel.setVisible(false);
        places_panel.setVisible(true);
        shortestPathList.getItems().clear();
    }

    /**
     * Lists the places on the places list view.
     *
     * @param placeList the list of place titles to be displayed
     */
    private void listPlaces(List<String> placeList) {
        placesList.getItems().clear();
        placesList.getItems().addAll(placeList);
    }

    /**
     * Highlights the first place in the filtered places list on the map by changing its color to red.
     * <p>
     * If the search place input field is empty, it shows all the places on the map.
     *
     * @param filteredPlaces the list of filtered places
     */
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

    /**
     * Lists the places with the specified facility type on the places list view, golf tour list view and highlights them on the map by changing their color to green using {@link WorldFXMLController#showPlacesWithFacility(FacilityType)} method.
     *
     * @param facilityType the facility type to be filtered
     */
    public void listPlacesWithFacility(FacilityType facilityType) {
        List<Place> facilityPlaces = World.getInstance().getPlacesWithFacility(facilityType);

        List<String> fpTitles = new ArrayList<>();

        facilityPlaces.forEach(place -> fpTitles.add(place.placeInfo()));
        listPlaces(fpTitles);
        listGolfCourses(facilityPlaces);

        showPlacesWithFacility(facilityType);
    }

    /**
     * This method displays all places on the world map that have a particular facility.
     *
     * @param facilityType the type of facility to filter places by
     */
    private void showPlacesWithFacility(FacilityType facilityType) {
        List<Place> facilityPlaces = World.getInstance().getPlacesWithFacility(facilityType);

        facilityPlaces.forEach(place -> {
            double x = scaleAxis(place.getLatitude());
            double y = scaleAxis(place.getLongitude());

            GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
            markPlace(graphicsContext, x, y, Color.GREEN);
        });
    }

    /**
     * Updates the golf courses list view with the given list of places using {@link dev.riko.golftourplanner.MainApp.GolfCourseListViewItem} class to handle checkboxes listener.
     * <p>
     * Highlights places with golf course on the map.
     *
     * @param placeList The list of places to display in the golf courses list view
     */
    private void listGolfCourses(List<Place> placeList) {
        golfCoursesList.getItems().clear();
        for (Place p : placeList) {
            MainApp.GolfCourseListViewItem golfCourse = new MainApp.GolfCourseListViewItem(FacilityType.GOLF_COURSE, p.getLatitude(), p.getLongitude(), p.getTitle(), p.getRating(), p.placeInfo(), false);

            golfCourse.onProperty().addListener((obs, wasOn, isNowOn) -> {
                double x = scaleAxis(p.getLatitude());
                double y = scaleAxis(p.getLongitude());

                GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
                if (wasOn) {
                    markPlace(graphicsContext, x, y, Color.GREEN);
                } else {
                    markPlace(graphicsContext, x, y, Color.RED);
                }
                System.out.println(golfCourse.getName() + " changed on state from " + wasOn + " to " + isNowOn);
            });

            golfCoursesList.getItems().add(golfCourse);
        }
    }

    /**
     * Filters the places by matching the place titles with the characters in the search place input field.
     * <p>
     * Updates the places list view with the filtered places and returns the filtered places.
     *
     * @return the list of filtered places
     */
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

        Collections.sort(placeTitles);

        placesList.getItems().clear();
        placesList.getItems().addAll(placeTitles);
        return filteredPlaces;
    }


    /**
     * This method displays the shortest path on the map by marking the places on the map with colors and drawing lines between them.
     *
     * @param shortestPath the list of places that are part of the shortest path.
     */
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

    /**
     * Marks a place on a graphics context with a given color at the specified coordinates.
     *
     * @param graphicsContext the graphics context where the place will be marked
     * @param x               the x coordinate where the place will be marked
     * @param y               the y coordinate where the place will be marked
     * @param color           the color of the place marker
     */
    private void markPlace(GraphicsContext graphicsContext, double x, double y, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x, y, 14, 14);
    }

    /**
     * Scales a given axis value based on the total amount and a fixed scale factor.
     *
     * @param x the value to be scaled
     * @return the scaled value
     */
    private double scaleAxis(double x) {
        return x * 100 / amount * 8.6;
    }

    /**
     * Closes the panel with tour creation.
     */
    @FXML
    private void closeTourPanel() {
        createTour_panel.setVisible(false);
        showPlacesOnMap(World.getInstance().getPlaceList());
        filterPlaces();
    }

    /**
     * Opens the create tour panel, shows all places with golf courses on the map, and lists places with a golf course facility.
     */
    @FXML
    private void openTourPanel() {
        createTour_panel.setVisible(true);
        showPlacesOnMap(World.getInstance().getPlaceList());
        listPlacesWithFacility(FacilityType.GOLF_COURSE);
    }

    /**
     * Shows the solo type form and hides the team type form.
     */
    @FXML
    private void showSoloForm() {
        teamTypePanel.setVisible(false);
        soloTypePanel.setVisible(true);
    }

    /**
     * Shows the team type form and hides the solo type form.
     */
    @FXML
    private void showTeamForm() {
        soloTypePanel.setVisible(false);
        teamTypePanel.setVisible(true);
    }

    /**
     * Swaps the start and final destinations entered by the user in the input fields.
     */
    @FXML
    private void swapDestinations() {
        try {
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
        } catch (RuntimeException e) {
            System.out.println("Nothing to swap!");
        }
    }

    /**
     * Shows the given golf tour on the map by displaying the places with golf courses and
     * the shortest path between them.
     *
     * @param golfTour A list of lists of Places representing the golf tour, where each inner list
     */
    public void showGolfTour(List<List<Place>> golfTour) {
        showPlacesOnMap(World.getInstance().getPlaceList());
        showPlacesWithFacility(FacilityType.GOLF_COURSE);

        for (List<Place> shortestPath : golfTour) {
            showShortestPathOnMap(shortestPath);
        }
    }
}
