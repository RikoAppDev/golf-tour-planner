package dev.riko.golftourplanner.place;

public class Place extends POI {
    public Place(double latitude, double longitude, String title, float rating) {
        super(latitude, longitude, title, rating);
    }

    public Place(double latitude, double longitude, String title) {
        super(latitude, longitude, title);
    }

    public void printPlace() {
        System.out.println("place: " + getTitle() + " | location: [" + String.format("%.2f", getLatitude()) + "; " + String.format("%.2f", getLongitude()) + "] | rating: " + String.format("%.2f", getRating()));
    }
}
