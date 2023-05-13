package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.exceptions.NoPathFoundException;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.place.Place;

import java.util.List;

/**
 * This class represents a search for the optimal trip between two places in a world map,
 * using Dijkstra's algorithm to find the shortest path between the places.
 */
public class SearchOptimalTrip {
    private final Place startPlace;
    private final Place finalPlace;
    private int exploringRate;
    private final Dijkstra dijkstra;
    private final List<Place> shortestPath;

    /**
     * Creates a new instance of SearchOptimalTrip with the given parameters, using {@link Dijkstra}'s algorithm to
     * find the shortest path between the start place and final place in the world map.
     *
     * @param world         The world map where the search is performed.
     * @param startPlace    The starting place for the search.
     * @param finalPlace    The final place for the search.
     * @param exploringRate The exploring rate for Dijkstra's algorithm.
     * @throws NoPathFoundException If no path can be found between the start place and final place in the world map.
     */
    public SearchOptimalTrip(World world, Place startPlace, Place finalPlace, int exploringRate) throws NoPathFoundException {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;
        this.exploringRate = exploringRate;

        dijkstra = new Dijkstra(world, startPlace, finalPlace);
        shortestPath = dijkstra.getTheShortestPath();
    }

    /**
     * Creates a new instance of SearchOptimalTrip with the given parameters, using {@link Dijkstra}'s algorithm to
     * find the shortest path between the start place and final place in the world map.
     *
     * @param world      The world map where the search is performed.
     * @param startPlace The starting place for the search.
     * @param finalPlace The final place for the search.
     * @throws NoPathFoundException If no path can be found between the start place and final place in the world map.
     */
    public SearchOptimalTrip(World world, Place startPlace, Place finalPlace) throws NoPathFoundException {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;

        dijkstra = new Dijkstra(world, startPlace, finalPlace);
        shortestPath = dijkstra.getTheShortestPath();
    }

    /**
     * Calculates the length of the shortest path found by Dijkstra's algorithm.
     *
     * @return The length of the shortest path found by Dijkstra's algorithm.
     */
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

    /**
     * Calculates the air distance between the start place and final place.
     *
     * @return The air distance between the start place and final place.
     */
    public double getAirDistanceLength() {
        return calculateLength(startPlace, finalPlace);
    }

    /**
     * Calculates the distance between two places using their distanceFrom method.
     *
     * @param first  The first place.
     * @param second The second place.
     * @return The distance between the two places.
     */
    private double calculateLength(Place first, Place second) {
        return first.distanceFrom(second);
    }

    /**
     * Gets the shortest path between the start and final places.
     *
     * @return the shortest path
     */
    public List<Place> getShortestPath() {
        return shortestPath;
    }
}
