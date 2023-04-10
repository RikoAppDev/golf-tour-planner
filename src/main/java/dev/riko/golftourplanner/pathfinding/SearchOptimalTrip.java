package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;

import java.util.List;

public class SearchOptimalTrip {
    private final Place startPlace;
    private final Place finalPlace;
    private final int exploringRate;
    private final Dijkstra dijkstra;
    private final List<Place> shortestPath;

    public SearchOptimalTrip(World world, Place startPlace, Place finalPlace, int exploringRate) {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;
        this.exploringRate = exploringRate;

        dijkstra = new Dijkstra(world, startPlace, finalPlace);
        shortestPath = dijkstra.getShortestPath();
    }

    public String printShortestPath() {
        String s = "";

        for (Place place : shortestPath) {
            s = s.concat(place.getTitle() + " -> ");
        }
        s = s.substring(0, s.length() - 4);

        return s;
    }

    public double getShortestPathLength() {
        double length = 0;
        List<Place> placeList = getShortestPath();

        for (int i = 0; i < placeList.size() - 1; i++) {
            Place first = placeList.get(i);
            Place second = placeList.get(i + 1);
            length += first.distanceFrom(second);
        }

        return length;
    }

    public double getAirDistanceLength() {
        return calculateLength(startPlace, finalPlace);
    }

    private double calculateLength(Place first, Place second) {
        return first.distanceFrom(second);
    }

    public List<Place> getShortestPath() {
        return shortestPath;
    }
}
