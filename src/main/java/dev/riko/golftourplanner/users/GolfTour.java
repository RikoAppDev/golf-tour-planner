package dev.riko.golftourplanner.users;

import dev.riko.golftourplanner.exeptions.NoPathFound;
import dev.riko.golftourplanner.pathfinding.SearchOptimalTrip;
import dev.riko.golftourplanner.world.World;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;

import java.util.List;

/**
 * GolfTour represents a golf tour plan of a participant.
 * It contains information about the participant, the start and final destination, a list of places with golf courses, the golf tour plan, and the total length of the tour.
 */
public class GolfTour {
    /**
     * The participant who plans the golf tour.
     */
    private final Participant participant;
    /**
     * The list of places with golf courses to be visited in the golf tour.
     */
    private final List<Place> placeWithGolfCourses;
    /**
     * The starting destination of the golf tour.
     */
    private final Place startDestination;
    /**
     * The final destination of the golf tour.
     */
    private final Place finalDestination;
    /**
     * The list of paths connecting the places with golf courses to form the golf tour.
     */
    private final List<List<Place>> golfTour;
    /**
     * The total length of the golf tour.
     */
    private double tourLength = 0;

    /**
     * Constructs a GolfTour with given parameters.
     *
     * @param participant          the participant taking the golf tour
     * @param startDestination     the starting destination of the tour
     * @param placeWithGolfCourses a list of places that have golf courses
     */
    public GolfTour(Participant participant, Place startDestination, List<Place> placeWithGolfCourses) throws NoPathFound {
        this.participant = participant;
        this.placeWithGolfCourses = placeWithGolfCourses;
        this.startDestination = startDestination;
        this.finalDestination = startDestination;

        golfTour = planGolfTour();
    }

    /**
     * Constructs a GolfTour with given parameters.
     *
     * @param participant          the participant taking the golf tour
     * @param startDestination     the starting destination of the tour
     * @param finalDestination     the final destination of the tour
     * @param placeWithGolfCourses a list of places that have golf courses
     */
    public GolfTour(Participant participant, Place startDestination, Place finalDestination, List<Place> placeWithGolfCourses) throws NoPathFound {
        this.participant = participant;
        this.placeWithGolfCourses = placeWithGolfCourses;
        this.startDestination = startDestination;
        this.finalDestination = finalDestination;

        golfTour = planGolfTour();
    }

    /**
     * Plans the golf tour by finding the shortest path between each pair of places with a golf course.
     *
     * @return a list of lists representing the golf tour
     */
    private List<List<Place>> planGolfTour() throws NoPathFound {
        placeWithGolfCourses.add(0, startDestination);
        placeWithGolfCourses.add(placeWithGolfCourses.size() - 1, finalDestination);

        World world = World.getInstance();

        Place startPlace = placeWithGolfCourses.get(0);
        Place finalPlace = placeWithGolfCourses.get(1);

        SearchOptimalTrip searchOptimalTrip = new SearchOptimalTrip(world, startPlace, finalPlace);
        golfTour.add(searchOptimalTrip.getShortestPath());
        tourLength += searchOptimalTrip.getShortestPathLength();

        for (int i = 1; i < placeWithGolfCourses.size() - 1; i++) {
            startPlace = placeWithGolfCourses.get(i);
            finalPlace = placeWithGolfCourses.get(i + 1);

            searchOptimalTrip = new SearchOptimalTrip(world, startPlace, finalPlace);
            golfTour.add(searchOptimalTrip.getShortestPath());
            tourLength += searchOptimalTrip.getShortestPathLength();

            double fee = placeWithGolfCourses.get(i).getFacility(FacilityType.GOLF_COURSE).getRating() * 10;
            if (participant instanceof Team) {
                participant.decreaseBudget(fee * ((Team) participant).getGolfers().size());
            } else {
                participant.decreaseBudget(fee);
            }
        }

        return golfTour;
    }

    /**
     * Returns the participant of this golf tour.
     *
     * @return the participant of this golf tour
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * Returns the list of places with golf courses that will be visited during this golf tour.
     *
     * @return the list of places with golf courses that will be visited during this golf tour
     */
    public List<Place> getPlaceWithGolfCourses() {
        return placeWithGolfCourses;
    }

    /**
     * Returns the starting destination of this golf tour.
     *
     * @return the starting destination of this golf tour
     */
    public Place getStartDestination() {
        return startDestination;
    }

    /**
     * Returns the final destination of this golf tour.
     *
     * @return the final destination of this golf tour
     */
    public Place getFinalDestination() {
        return finalDestination;
    }

    /**
     * Returns the list of lists of places that represent the golf tour planned for this golf tour.
     *
     * @return the list of lists of places that represent the golf tour planned for this golf tour
     */
    public List<List<Place>> getGolfTour() {
        return golfTour;
    }

    /**
     * Returns the length of the golf tour planned for this golf tour.
     *
     * @return the length of the golf tour planned for this golf tour
     */
    public double getTourLength() {
        return tourLength;
    }
}
