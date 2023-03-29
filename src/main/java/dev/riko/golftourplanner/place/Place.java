package dev.riko.golftourplanner.place;

import dev.riko.golftourplanner.facility.FacilityType;

import java.util.List;

public class Place extends POI {
    private final List<FacilityType> facilityTypeList;
    private final int population;
    private final float peopleFlow;
    private final int roadsCount;

    public Place(double latitude, double longitude, String title, float rating, List<FacilityType> facilities, int population, float peopleFlow, int roadsCount) {
        super(latitude, longitude, title, rating);
        this.facilityTypeList = facilities;
        this.population = population;
        this.peopleFlow = peopleFlow;
        this.roadsCount = roadsCount;
    }

    public Place(double latitude, double longitude, String title, List<FacilityType> facilities, int population, float peopleFlow, int roadsCount) {
        super(latitude, longitude, title);
        this.facilityTypeList = facilities;
        this.population = population;
        this.peopleFlow = peopleFlow;
        this.roadsCount = roadsCount;
    }

    public void printPlace() {
        System.out.println("place: " + getTitle() + " | location: [" + String.format("%.2f", getLatitude()) + "; " + String.format("%.2f", getLongitude()) + "] | population: " + getPopulation() + " | rating: " + String.format("%.2f", getRating()) + " | facilities: " + getFacilityList() + " | connections: " + getRoadsCount());
    }

    public List<FacilityType> getFacilityList() {
        return facilityTypeList;
    }

    public int getPopulation() {
        return population;
    }

    public float getPeopleFlow() {
        return peopleFlow;
    }

    public int getRoadsCount() {
        return roadsCount;
    }
}
