package dev.riko.golftourplanner.world.place;

import dev.riko.golftourplanner.world.facility.Facility;
import dev.riko.golftourplanner.world.facility.FacilityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Place extends POI {
    private final List<Facility> facilityList;
    private final int population;
    private final int roadsCount;
    private final Set<Place> placeConnections = new HashSet<>();

    public Place(double latitude, double longitude, String title, float rating, List<Facility> facilities, int population, int roadsCount) {
        super(latitude, longitude, title, rating);
        this.facilityList = facilities;
        this.population = population;
        this.roadsCount = roadsCount;
    }

    public Place(double latitude, double longitude, String title, List<Facility> facilities, int population, int roadsCount) {
        super(latitude, longitude, title);
        this.facilityList = facilities;
        this.population = population;
        this.roadsCount = roadsCount;
    }

    public String placeInfo() {
        return "🏙️ " + getTitle() + " | 🛰️ [" + String.format("%.2f", getLatitude()) + "; " + String.format("%.2f", getLongitude()) + "] |  ️👨🏼‍👩🏼‍👧🏼‍👦🏼 " + getPopulation() + " | " + String.format("%.2f", getRating()) + " ⭐ | " + printFacilities() + " | " + getPlaceConnections().size() + " 🛣️ " + printPlaceConnections();
    }

    private String printFacilities() {
        String facilities = "";
        for (Facility facility : facilityList) {
            switch (facility.getFacilityType()) {
                case HOTEL -> facilities = facilities.concat("🏨");
                case GOLF_COURSE -> facilities = facilities.concat("⛳");
                case AIRPORT -> facilities = facilities.concat("🛫");
                case RESTAURANTS -> facilities = facilities.concat("🍜");
                case HOSTEL -> facilities = facilities.concat("🏩");
                case SHOPPING_MALL -> facilities = facilities.concat("🏬");
                case GAS_STATION -> facilities = facilities.concat("⛽");
                case SUPERMARKET -> facilities = facilities.concat("🛒");
                case SHOP -> facilities = facilities.concat("🏪");
            }
        }
        return facilities;
    }

    private String printPlaceConnections() {
        String connections = "";
        for (Place place : placeConnections) {
            connections = connections.concat(place.getTitle() + ", ");
        }
        if (connections.equals(""))
            return connections;
        else
            return connections.substring(0, connections.length() - 2);
    }

    public boolean hasFacility(FacilityType facilityType) {
        for (Facility facility : facilityList) {
            if (facility.getFacilityType() == facilityType)
                return true;
        }
        return false;
    }

    public List<Facility> getFacilityList() {
        return facilityList;
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

    public int getRoadsCount() {
        return roadsCount;
    }
}
