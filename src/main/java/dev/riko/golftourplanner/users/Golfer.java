package dev.riko.golftourplanner.users;

/**
 * A class representing a golfer, extending the Person class and implementing the Budget interface.
 */
public class Golfer extends Person implements Budget {
    private double budget;
    private double hcp;
    private String club;

    /**
     * Constructs a Golfer object with the specified parameters.
     *
     * @param firstname the first name of the golfer
     * @param lastname  the last name of the golfer
     * @param age       the age of the golfer
     * @param budget    the budget of the golfer
     * @param hcp       the handicap of the golfer
     * @param club      the golf club of the golfer
     */
    public Golfer(String firstname, String lastname, int age, double budget, double hcp, String club) {
        super(firstname, lastname, age);
        this.budget = budget;
        this.hcp = hcp;
        this.club = club;
    }

    /**
     * Returns the budget of the golfer.
     *
     * @return the budget of the golfer
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the golfer to the specified value.
     *
     * @param budget the new budget of the golfer
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Increases the budget of the golfer by the specified amount.
     *
     * @param budget the amount to increase the budget by
     */
    @Override
    public void increaseBudget(double budget) {

    }

    /**
     * Decreases the budget of the golfer by the specified amount.
     *
     * @param budget the amount to decrease the budget by
     */
    @Override
    public void decreaseBudget(double budget) {

    }

    /**
     * Returns the handicap of the golfer.
     *
     * @return the handicap of the golfer
     */
    public double getHcp() {
        return hcp;
    }

    /**
     * Sets the handicap of the golfer to the specified value.
     *
     * @param hcp the new handicap of the golfer
     */
    public void setHcp(double hcp) {
        this.hcp = hcp;
    }

    /**
     * Returns the golf club of the golfer.
     *
     * @return the golf club of the golfer
     */
    public String getClub() {
        return club;
    }

    /**
     * Sets the golf club of the golfer to the specified value.
     *
     * @param club the new golf club of the golfer
     */
    public void setClub(String club) {
        this.club = club;
    }
}
