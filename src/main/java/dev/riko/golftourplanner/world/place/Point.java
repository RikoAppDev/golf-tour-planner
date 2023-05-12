package dev.riko.golftourplanner.world.place;

/**
 * The {@code Point} class represents a point on 2D map with specified coordinates.
 */
public class Point {
    /**
     * The latitude of the point.
     */
    private final double latitude;

    /**
     * The longitude of the point.
     */
    private final double longitude;

    /**
     * Creates a new point with the given coordinates.
     *
     * @param latitude  the latitude of the point
     * @param longitude the longitude of the point
     */
    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculates the distance between this point and the given point.
     *
     * @param point the other point
     * @return the distance between this point and the given point
     */
    public double distanceFrom(Point point) {
        double dx = point.latitude - this.latitude;
        double dy = point.longitude - this.longitude;

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns the latitude of the point.
     *
     * @return the latitude of the point
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the point.
     *
     * @return the longitude of the point
     */
    public double getLongitude() {
        return longitude;
    }
}
