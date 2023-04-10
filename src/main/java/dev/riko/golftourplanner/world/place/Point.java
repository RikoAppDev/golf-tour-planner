package dev.riko.golftourplanner.world.place;

public class Point {
    private final double latitude, longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceFrom(Point point) {
        double dx = point.latitude - this.latitude;
        double dy = point.longitude - this.longitude;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
