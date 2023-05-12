package dev.riko.golftourplanner;

import dev.riko.golftourplanner.controlers.WorldFXMLController;
import dev.riko.golftourplanner.exeptions.*;
import dev.riko.golftourplanner.pathfinding.SearchOptimalTrip;
import dev.riko.golftourplanner.users.GolfTour;
import dev.riko.golftourplanner.users.Golfer;
import dev.riko.golftourplanner.users.Participant;
import dev.riko.golftourplanner.users.Team;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The MainApp class is responsible for handling the JavaFX application lifecycle and user interface events.
 */
public class MainApp extends Application {
    /**
     * This method is responsible for starting the JavaFX application by loading the world.fxml file, setting the controller and initializing the user interface elements.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException Thrown when an I/O exception to some sort has occurred.
     */
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
            Thread thread = new Thread(() -> {
                try {
                    String startDestination = String.valueOf(worldFXMLController.startDestinationInput.getCharacters()).strip();
                    String finalDestination = String.valueOf(worldFXMLController.finalDestinationInput.getCharacters()).strip();
                    if (startDestination.equals("") || finalDestination.equals("")) {
                        throw new MissingDestinationException();
                    } else if (!world.searchCity(startDestination)) {
                        throw new UnknownPlaceException(startDestination);
                    } else if (!world.searchCity(finalDestination)) {
                        throw new UnknownPlaceException(finalDestination);
                    } else if (startDestination.equalsIgnoreCase(finalDestination)) {
                        throw new SamePlacesException();
                    }

                    Place startPlace;
                    Place finalPlace;

                    List<Place> startPlaces = world.getPlaces(startDestination);
                    startPlace = startPlaces.get(0);
                    List<Place> finalPlaces = world.getPlaces(finalDestination);
                    finalPlace = finalPlaces.get(0);

                    SearchOptimalTrip optimalTrip = new SearchOptimalTrip(world, startPlace, finalPlace);
                    Platform.runLater(() -> {
                        worldFXMLController.showPlacesOnMap(world.getPlaceList());
                        worldFXMLController.places_panel.setVisible(false);
                        worldFXMLController.pathfindingInfo_panel.setVisible(true);
                        worldFXMLController.airDistanceLabel.setText("Air distance from " + startPlace.getTitle() + " to " + finalPlace.getTitle() + " is " + String.format("%.2f", optimalTrip.getAirDistanceLength()) + "km.");
                        worldFXMLController.routeLength.setText("Length of the route: " + String.format("%.2f", optimalTrip.getShortestPathLength()) + "km");

                        List<String> routePlaces = new ArrayList<>();

                        for (Place place : optimalTrip.getShortestPath()) {
                            routePlaces.add(place.placeInfo());
                            routePlaces.add("⬇️");
                        }
                        routePlaces = routePlaces.subList(0, routePlaces.size() - 1);
                        worldFXMLController.shortestPathList.getItems().clear();
                        worldFXMLController.shortestPathList.getItems().addAll(routePlaces);

                        worldFXMLController.showShortestPathOnMap(optimalTrip.getShortestPath());
                    });
                } catch (MissingDestinationException e) {
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.initOwner(stage);
                        a.setTitle("Warning");
                        a.setContentText("Route cannot be calculated because of missing destination.");
                        a.showAndWait();
                    });
                } catch (NullPointerException e) {
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.initOwner(stage);
                        a.setTitle("Error");
                        a.setContentText("Firstly you need to generate data!!!");
                        a.showAndWait();
                    });
                } catch (UnknownPlaceException e) {
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.initOwner(stage);
                        a.setTitle("Warning");
                        a.setContentText("City " + e.getMessage() + " does not exist.");
                        a.showAndWait();
                    });
                } catch (NoPathFound e) {
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.initOwner(stage);
                        a.setTitle("Error");
                        a.setContentText("Shortest path cannot be found, try regenerate the map.");
                        a.showAndWait();
                    });
                } catch (SamePlacesException e) {
                    Platform.runLater(() -> {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.initOwner(stage);
                        a.setTitle("Information");
                        a.setContentText("Places can not be same.");
                        a.showAndWait();
                    });
                }
            });
            thread.start();
        });

        worldFXMLController.searchPlaceBtn.setOnAction(event -> {
            try {
                List<Place> filteredPlaces = worldFXMLController.filterPlaces();
                worldFXMLController.highlightPlaceOnMap(filteredPlaces);
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            } catch (IndexOutOfBoundsException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("City does not exist!!!");
                a.showAndWait();
            }
        });
        worldFXMLController.searchPlaceInput.setOnAction(event -> {
            try {
                List<Place> filteredPlaces = worldFXMLController.filterPlaces();
                worldFXMLController.highlightPlaceOnMap(filteredPlaces);
            } catch (NullPointerException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("Firstly you need to generate data!!!");
                a.showAndWait();
            } catch (IndexOutOfBoundsException e) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.initOwner(stage);
                a.setTitle("Warning");
                a.setContentText("City does not exist!!!");
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

        worldFXMLController.generateTourBtn.setOnAction(event -> {
            try {
                RadioButton selected = (RadioButton) worldFXMLController.tourType.getSelectedToggle();
                String b = worldFXMLController.budgetField.getText();
                if (b.strip().length() == 0) {
                    throw new MissingBudgetException();
                }
                double budget = Double.parseDouble(b);

                Participant participant;
                if (selected.getText().equals("Solo")) {
                    String firstname = worldFXMLController.firstname.getText();
                    String lastname = worldFXMLController.lastname.getText();
                    if (firstname.strip().length() == 0 || lastname.strip().length() == 0) {
                        throw new MissingNameException();
                    }

                    String a = worldFXMLController.age.getText();
                    if (a.strip().length() == 0) {
                        throw new MissingAgeException();
                    }
                    int age = Integer.parseInt(a);

                    String h = worldFXMLController.hcp.getText();
                    if (h.strip().length() == 0) {
                        throw new MissingHcpException();
                    }
                    double hcp = Double.parseDouble(h);
                    String club = worldFXMLController.club.getText().strip();

                    participant = new Golfer(firstname, lastname, age, hcp, club);
                } else {
                    String teamName = worldFXMLController.teamName.getText().strip();
                    if (teamName.length() == 0) {
                        throw new MissingNameException();
                    }

                    String teamSize = worldFXMLController.teamSize.getText().strip();
                    if (teamSize.length() == 0) {
                        throw new IncorrectTeamSizeException();
                    }
                    int teamCount;
                    try {
                        teamCount = Integer.parseInt(teamSize);
                        if (teamCount < 1) {
                            throw new IncorrectTeamSizeException();
                        }
                    } catch (NumberFormatException e) {
                        throw new IncorrectTeamSizeException();
                    }

                    List<Golfer> teamGolfers = new ArrayList<>();
                    for (int i = 0; i < teamCount; i++) {
                        teamGolfers.add(new Golfer());
                    }
                    participant = new Team(teamName);

                    ((Team) participant).setGolfers(teamGolfers);
                }
                participant.setBudget(budget);

                String startPlace = worldFXMLController.startPlace.getText();
                String finalPlace = worldFXMLController.finalPlace.getText();
                if (startPlace.strip().length() == 0 || finalPlace.strip().length() == 0) {
                    throw new MissingDestinationException();
                }

                if (!world.searchCity(startPlace)) {
                    throw new UnknownPlaceException(startPlace);
                } else if (!world.searchCity(finalPlace)) {
                    throw new UnknownPlaceException(finalPlace);
                }

                Place sp = world.getPlaces(startPlace).get(0);
                Place fp = world.getPlaces(finalPlace).get(0);

                List<Place> selectedGolfCoursePlaces = new ArrayList<>();

                if (sp.equals(fp)) {
                    GolfTour golfTour = new GolfTour(participant, sp, selectedGolfCoursePlaces);
                } else {
                    GolfTour golfTour = new GolfTour(participant, sp, fp, selectedGolfCoursePlaces);
                }
            } catch (NoPathFound e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.initOwner(stage);
                    a.setTitle("Error");
                    a.setContentText("Shortest path cannot be found, try regenerate the map.");
                    a.showAndWait();
                });
            } catch (MissingBudgetException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Set the budget!");
                    a.showAndWait();
                });
            } catch (MissingDestinationException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Tour cannot be generated because of missing destination.");
                    a.showAndWait();
                });
            } catch (MissingNameException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Name is empty.");
                    a.showAndWait();
                });
            } catch (MissingAgeException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Age is empty.");
                    a.showAndWait();
                });
            } catch (MissingHcpException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("HCP is empty.");
                    a.showAndWait();
                });
            } catch (NumberFormatException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Wrong number format. (e.g. budget -> 99.99, hcp -> 54.0)");
                    a.showAndWait();
                });
            } catch (UnknownPlaceException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("City " + e.getMessage() + " does not exist.");
                    a.showAndWait();
                });
            } catch (IncorrectTeamSizeException e) {
                Platform.runLater(() -> {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.initOwner(stage);
                    a.setTitle("Warning");
                    a.setContentText("Team size is empty or incorrect.");
                    a.showAndWait();
                });
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

    /**
     * Launches the JavaFX application.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch();
    }
}
