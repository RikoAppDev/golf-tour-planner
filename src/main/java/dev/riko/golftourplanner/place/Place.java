package dev.riko.golftourplanner.place;

import dev.riko.golftourplanner.facility.FacilityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Place extends POI {
    private final List<FacilityType> facilityTypeList;
    private final int population;
    private final Set<Place> placeConnections = new HashSet<>();

    public Place(double latitude, double longitude, String title, float rating, List<FacilityType> facilities, int population) {
        super(latitude, longitude, title, rating);
        this.facilityTypeList = facilities;
        this.population = population;
    }

    public Place(double latitude, double longitude, String title, List<FacilityType> facilities, int population) {
        super(latitude, longitude, title);
        this.facilityTypeList = facilities;
        this.population = population;
    }

    public void printPlace() {
        System.out.println("place: " + getTitle() + " | location: [" + String.format("%.2f", getLatitude()) + "; " + String.format("%.2f", getLongitude()) + "] | population: " + getPopulation() + " | rating: " + String.format("%.2f", getRating()) + " | facilities: " + getFacilityList() + " | connections " + getPlaceConnections().size() + ": " + printPlaceConnections());
    }

    private String printPlaceConnections() {
        String connections = "";
        for (Place place : placeConnections) {
            connections = connections.concat(place.getTitle() + "; ");
        }
        if (connections.equals(""))
            return connections;
        else
            return connections.substring(0, connections.length() - 2);
    }

    public boolean hasFacility(FacilityType facilityType) {
        return facilityTypeList.contains(facilityType);
    }

    public List<FacilityType> getFacilityList() {
        return facilityTypeList;
    }

    public int getPopulation() {
        return population;
    }

    public Set<Place> getPlaceConnections() {
        return placeConnections;
    }

    public boolean addToPlaceConnections(Place place) {
        return placeConnections.add(place);
    }
}
