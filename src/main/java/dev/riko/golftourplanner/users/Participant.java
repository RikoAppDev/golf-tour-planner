package dev.riko.golftourplanner.users;

/**
 * A class representing a participant with a budget.
 * <p>
 * Implements the Budget interface to provide methods for managing the budget.
 */
public abstract class Participant implements Budget {
    /**
     * The current budget of the participant.
     */
    private double budget;

    /**
     * Returns the current budget of the participant.
     *
     * @return the current budget of the participant
     */
    @Override
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the participant.
     *
     * @param budget the new budget of the participant
     */
    @Override
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Increases the budget of the participant by the specified amount.
     *
     * @param budget the amount by which to increase the budget
     */
    @Override
    public void increaseBudget(double budget) {
        this.budget += budget;
    }

    /**
     * Decreases the budget of the participant by the specified amount.
     *
     * @param budget the amount by which to decrease the budget
     */
    @Override
    public void decreaseBudget(double budget) {
        this.budget -= budget;
    }
}
