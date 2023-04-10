package dev.riko.golftourplanner.world.place;

public class POI extends Point {

    private final String title;
    private float rating = 0;

    public POI(double latitude, double longitude, String title, float rating) {
        super(latitude, longitude);
        this.title = title;
        this.rating = rating;
    }

    public POI(double latitude, double longitude, String title) {
        super(latitude, longitude);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public float getRating() {
        return rating;
    }
}
