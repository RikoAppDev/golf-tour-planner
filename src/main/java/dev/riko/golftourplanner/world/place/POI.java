package dev.riko.golftourplanner.world.place;

/**
 * The {@code POI} (Point of Interest) class represents a point of interest with specified coordinates, a title, and a rating.
 * It extends the {@link Point} class.
 */
public class POI extends Point {

    /** The title of the POI. */
    private final String title;

    /** The rating of the POI, default value is 0. */
    private float rating = 0;

    /**
     * Creates a new POI with the given coordinates, title, and rating.
     *
     * @param latitude the latitude of the POI
     * @param longitude the longitude of the POI
     * @param title the title of the POI
     * @param rating the rating of the POI
     */
    public POI(double latitude, double longitude, String title, float rating) {
        super(latitude, longitude);
        this.title = title;
        this.rating = rating;
    }

    /**
     * Creates a new POI with the given coordinates and title.
     * The rating is set to the default value of 0.
     *
     * @param latitude the latitude of the POI
     * @param longitude the longitude of the POI
     * @param title the title of the POI
     */
    public POI(double latitude, double longitude, String title) {
        super(latitude, longitude);
        this.title = title;
    }

    /**
     * Returns the title of the POI.
     *
     * @return the title of the POI
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the rating of the POI.
     *
     * @return the rating of the POI
     */
    public float getRating() {
        return rating;
    }
}
