package dev.riko.golftourplanner.world;

import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code World} class represents a world that contains a list of places.
 * It is a singleton class, meaning there can only be one instance of it in the system.
 */
public class World {
    /**
     * The single instance of the World class.
     */
    private static final World worldInstance = new World();
    /**
     * The list of places contained in the world.
     */
    private List<Place> placeList;

    /**
     * Private constructor to prevent direct instantiation of the World class.
     */
    private World() {
    }

    /**
     * Returns the single instance of the World class.
     *
     * @return the single instance of the World class
     */
    public static World getInstance() {
        return worldInstance;
    }

    /**
     * Sets the list of places contained in the world.
     *
     * @param placeList the list of places to set
     */
    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    /**
     * Returns a list of places in the world that have a given facility type.
     *
     * @param facilityType the facility type to search for
     * @return a list of places in the world that have the given facility type
     */
    public List<Place> getPlacesWithFacility(FacilityType facilityType) {
        List<Place> places = new ArrayList<>();
        for (Place place : placeList) {
            if (place.hasFacility(facilityType)) {
                places.add(place);
            }
        }

        return places;
    }

    /**
     * Searches for a city in the world.
     *
     * @param city the name of the city to search for
     * @return {@code true} if the city is found, {@code false} otherwise
     */
    public boolean searchCity(String city) {
        return searchCity(city, false);
    }

    /**
     * Searches for a city in the world and optionally prints information about the city.
     *
     * @param city  the name of the city to search for
     * @param print whether to print information about the city
     * @return {@code true} if the city is found, {@code false} otherwise
     */
    public boolean searchCity(String city, boolean print) {
        boolean e = false;
        for (Place place : placeList) {
            if (place.getTitle().equalsIgnoreCase(city)) {
                if (print) System.out.println(place.placeInfo());
                e = true;
            }
        }

        return e;
    }

    /**
     * Returns a place for a given city title.
     *
     * @param city the name of the city to search for
     * @return a place for a given city title
     */
    public Place getPlace(String city) {
        for (Place p : placeList) {
            if (p.getTitle().equalsIgnoreCase(city)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns the list of places contained in the world.
     *
     * @return the list of places contained in the world
     */
    public List<Place> getPlaceList() {
        return placeList;
    }
}
