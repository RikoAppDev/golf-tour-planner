package dev.riko.golftourplanner.world;

import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static final World worldInstance = new World();
    private List<Place> placeList;

    private World() {
    }

    public static World getInstance() {
        return worldInstance;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public void showPlaces() {
        for (int i = 0; i < placeList.size(); i++) {
            System.out.print((i + 1) + ". ");
            System.out.println(placeList.get(i).placeInfo());
        }
    }

    public void showPlacesWithFacility(FacilityType facilityType) {
        for (Place place : placeList) {
            if (place.hasFacility(facilityType)) {
                System.out.println(place.placeInfo());
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
                if (print) System.out.println(place.placeInfo());
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
