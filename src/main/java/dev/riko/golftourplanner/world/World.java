package dev.riko.golftourplanner.world;

import dev.riko.golftourplanner.facility.FacilityType;
import dev.riko.golftourplanner.place.Place;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Place> placeList;

    public World(List<Place> placeList) {
        this.placeList = placeList;
    }

    public void showPlaces() {
        for (int i = 0; i < placeList.size(); i++) {
            System.out.print((i + 1) + ". ");
            placeList.get(i).printPlace();
        }
    }

    public void showPlacesWithFacility(FacilityType facilityType) {
        for (Place place : placeList) {
            if (place.hasFacility(facilityType)) {
                place.printPlace();
            }
        }
    }

    public boolean searchCity(String city) {
        return searchCity(city, false);
    }

    public boolean searchCity(String city, boolean print) {
        boolean e = false;
        for (Place place : placeList) {
            if (place.getTitle().equalsIgnoreCase(city)) {
                if (print)
                    place.printPlace();
                e = true;
            }
        }

        return e;
    }

    public List<Place> getPlaces(String city) {
        List<Place> places = new ArrayList<>();
        for (Place p : placeList) {
            if (p.getTitle().equalsIgnoreCase(city)) {
                places.add(p);
            }
        }

        return places;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }
}
