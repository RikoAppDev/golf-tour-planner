package dev.riko.golftourplanner.world.facility;

import dev.riko.golftourplanner.world.place.POI;

/**
 * The {@code Facility} class represents a single facility with a specific facility type from enum class {@link FacilityType}.
 */
public class Facility extends POI {
    /**
     * The type of the facility.
     */
    private final FacilityType facilityType;

    /**
     * Creates a new facility with the given facility type and coordinates.
     *
     * @param facilityType the type of the facility
     * @param latitude     the latitude coordinate of the facility
     * @param longitude    the longitude coordinate of the facility
     */
    public Facility(FacilityType facilityType, double latitude, double longitude) {
        super(latitude, longitude);
        this.facilityType = facilityType;
    }

    /**
     * Creates a new facility with the given facility type, coordinates, title, and rating.
     *
     * @param facilityType the type of the facility
     * @param latitude     the latitude coordinate of the facility
     * @param longitude    the longitude coordinate of the facility
     * @param title        the title of the facility
     * @param rating       the rating of the facility
     */
    public Facility(FacilityType facilityType, double latitude, double longitude, String title, float rating) {
        super(latitude, longitude, title, rating);
        this.facilityType = facilityType;
    }

    /**
     * Returns the facility type of this facility.
     *
     * @return the facility type of this facility
     */
    public FacilityType getFacilityType() {
        return facilityType;
    }
}
