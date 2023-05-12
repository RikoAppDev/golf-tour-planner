package dev.riko.golftourplanner.world.place;

import dev.riko.golftourplanner.world.facility.Facility;
import dev.riko.golftourplanner.world.facility.FacilityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A subclass of POI that represents a Place on a map, with additional information about facilities, population, roads and connections to other Places.
 */
public class Place extends POI {
    /**
     * The list of facilities in the place.
     */
    private final List<Facility> facilityList;
    /**
     * The population of the place.
     */
    private final int population;
    /**
     * The minimal number of connection roads to the place.
     */
    private final int roadsCount;
    /**
     * The set of connections to other places.
     */
    private final Set<Place> placeConnections = new HashSet<>();

    /**
     * Creates a new Place object with the specified latitude, longitude, title, rating, facilities, population and roads count.
     *
     * @param latitude   the latitude of the place.
     * @param longitude  the longitude of the place.
     * @param title      the title of the place.
     * @param rating     the rating of the place.
     * @param facilities the list of facilities in the place.
     * @param population the population of the place.
     * @param roadsCount the number of roads in the place.
     */
    public Place(double latitude, double longitude, String title, float rating, List<Facility> facilities, int population, int roadsCount) {
        super(latitude, longitude, title, rating);
        this.facilityList = facilities;
        this.population = population;
        this.roadsCount = roadsCount;
    }

    /**
     * Creates a new Place object with the specified latitude, longitude, title, facilities, population and roads count.
     *
     * @param latitude   the latitude of the place.
     * @param longitude  the longitude of the place.
     * @param title      the title of the place.
     * @param facilities the list of facilities in the place.
     * @param population the population of the place.
     * @param roadsCount the number of roads in the place.
     */
    public Place(double latitude, double longitude, String title, List<Facility> facilities, int population, int roadsCount) {
        super(latitude, longitude, title);
        this.facilityList = facilities;
        this.population = population;
        this.roadsCount = roadsCount;
    }

    /**
     * Returns a string containing information about the place, including the title, latitude, longitude, population, rating, facilities, number of connections and names of connected places.
     *
     * @return a string containing information about the place.
     */
    public String placeInfo() {
        return "🏙️ " + getTitle() + " | 🛰️ [" + String.format("%.2f", getLatitude()) + "; " + String.format("%.2f", getLongitude()) + "] |  ️👨🏼‍👩🏼‍👧🏼‍👦🏼 " + getPopulation() + " | " + String.format("%.2f", getRating()) + " ⭐ | " + printFacilities() + " | " + getPlaceConnections().size() + " 🛣️ " + printPlaceConnections();
    }

    /**
     * Returns a string containing emoji representations of the facilities in the place.
     *
     * @return a string containing emoji representations of the facilities in the place.
     */
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

    /**
     * Returns a string containing the titles of all places connected to this place.
     *
     * @return a string containing the titles of all places connected to this place
     */
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

    /**
     * Determines whether this place has a facility of the specified facility type.
     *
     * @param facilityType the type of facility to check for
     * @return {@code true} if this place has a facility of the specified facility type, {@code false} otherwise
     */
    public boolean hasFacility(FacilityType facilityType) {
        for (Facility facility : facilityList) {
            if (facility.getFacilityType() == facilityType)
                return true;
        }
        return false;
    }

    /**
     * Returns a list of all facilities in this place.
     *
     * @return a list of all facilities in this place
     */
    public List<Facility> getFacilityList() {
        return facilityList;
    }

    /**
     * Returns the population of this place.
     *
     * @return the population of this place
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Returns a set of all places connected to this place.
     *
     * @return a set of all places connected to this place
     */
    public Set<Place> getPlaceConnections() {
        return placeConnections;
    }

    /**
     * Adds the place to the set of places connected to this place.
     *
     * @param place the place to add
     * @return {@code true} if the place was added, {@code false} otherwise
     */
    public boolean addToPlaceConnections(Place place) {
        return placeConnections.add(place);
    }

    /**
     * Returns the minimal number of connection roads to this place.
     *
     * @return the minimal number of connection roads to this place
     */
    public int getRoadsCount() {
        return roadsCount;
    }
}
