package dev.riko.golftourplanner.pathfinding;

import dev.riko.golftourplanner.world.place.Place;

public class SearchOptimalTrip {
    private final Place startPlace;
    private final Place finalPlace;
    private double tripLength = 0;
    private final int exploringRate;

    public SearchOptimalTrip(Place startPlace, Place finalPlace, int exploringRate) {
        this.startPlace = startPlace;
        this.finalPlace = finalPlace;
        this.exploringRate = exploringRate;
    }

    public double getOptimalTripLength() {
        this.tripLength = calculateLength(startPlace, finalPlace);
        return tripLength;
    }

    private double calculateLength(Place startPlace, Place finalPlace) {
        return startPlace.distanceFrom(finalPlace);
    }
}
