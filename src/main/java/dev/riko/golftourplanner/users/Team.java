package dev.riko.golftourplanner.users;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a golf team.
 */
public class Team implements Budget {
    private String name;
    private double budget;
    private List<Golfer> golfers = new ArrayList<>();

    /**
     * Constructs a new Team object with the given name and budget.
     *
     * @param name   the name of the team
     * @param budget the budget of the team
     */
    public Team(String name, double budget) {
        this.name = name;
        this.budget = budget;
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

    @Override
    public void setBudget(double budget) {

    }

    @Override
    public void increaseBudget(double budget) {

    }

    @Override
    public void decreaseBudget(double budget) {

    }

    @Override
    public double getBudget() {
        return 0;
    }
}
