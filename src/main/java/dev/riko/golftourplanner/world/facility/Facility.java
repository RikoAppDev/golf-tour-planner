package dev.riko.golftourplanner.world.facility;

/**
 * The {@code Facility} class represents a single facility with a specific facility type from enum class {@link FacilityType}.
 */
public class Facility {
    /**
     * The type of the facility.
     */
    private final FacilityType facilityType;

    /**
     * Creates a new facility with the given facility type.
     *
     * @param facilityType the type of the facility
     */
    public Facility(FacilityType facilityType) {
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
