package dev.riko.golftourplanner.world.facility;

public class Facility {
    private final FacilityType facilityType;

    public Facility(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }
}
