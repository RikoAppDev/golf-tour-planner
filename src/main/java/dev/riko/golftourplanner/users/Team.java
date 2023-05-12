package dev.riko.golftourplanner.users;

import java.util.ArrayList;
import java.util.List;

/**
 * The Team class represents a team of golfers participating in a golf tournament.
 * <p>
 * Each team has a name and a list of Golfers who are part of the team. Teams also have a budget which can be used to cover expenses related to participating in the tournament.
 */
public class Team extends Participant {
    /**
     * The name of the team.
     */
    private String name;
    /**
     * A list of Golfers on the team.
     */
    private List<Golfer> golfers = new ArrayList<>();

    /**
     * Constructs a new Team object with the given name.
     *
     * @param name the name of the team
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the team.
     *
     * @return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name the name of the team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a Golfer to the team.
     *
     * @param golfer the Golfer to add
     */
    public void addGolfer(Golfer golfer) {
        golfers.add(golfer);
    }

    /**
     * Returns a list of Golfers on the team.
     *
     * @return a list of Golfers on the team
     */
    public List<Golfer> getGolfers() {
        return golfers;
    }

    /**
     * Sets the list of Golfers on the team.
     *
     * @param golfers a list of Golfers to set on the team
     */
    public void setGolfers(List<Golfer> golfers) {
        this.golfers = golfers;
    }
}
