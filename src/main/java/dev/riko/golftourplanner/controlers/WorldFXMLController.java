package dev.riko.golftourplanner.controlers;

import dev.riko.golftourplanner.utils.GenerateData;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
    private Canvas worldMap;

    public void generatePlaces() {
        try {
            int amount = Integer.parseInt(placesAmountInput.getCharacters().toString());
            GenerateData generateData = new GenerateData(amount);

            World world = World.getInstance();
            world.setPlaceList(generateData.getData());
            showPlaces(world.getPlaceList());
            List<String> placeTitles = new ArrayList<>();
            world.getPlaceList().forEach(place -> {
                placeTitles.add(place.getTitle());
            });
            listPlaces(placeTitles);
            infoLabel.setVisible(false);
        } catch (Exception e) {
            System.out.println("Incorrect input!!!");
        }
    }

    private void showPlaces(List<Place> placeList) {
        GraphicsContext graphicsContext = worldMap.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, worldMap.getWidth(), worldMap.getHeight());

        worldMap.setHeight(874);
        worldMap.setWidth(874);
        placeList.forEach(place -> {
            double y = place.getLongitude() * 8.6;
            double x = place.getLatitude() * 8.6;

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillOval(x, y, 14, 14);
            graphicsContext.setFill(Color.GRAY);

            place.getPlaceConnections().forEach(connection -> {
                double y1 = connection.getLongitude() * 8.6;
                double x1 = connection.getLatitude() * 8.6;

                graphicsContext.strokeLine(x + 7, y + 7, x1 + 7, y1 + 7);
            });
        });
        worldMap.autosize();
    }

    private void listPlaces(List<String> placeList) {
        placesList.getItems().clear();
        placesList.getItems().addAll(placeList);
    }
}
