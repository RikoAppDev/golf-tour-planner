package dev.riko.golftourplanner.users;

/**
 * A budget interface for objects that have a budget.
 */
public interface Budget {
    /**
     * Returns the current budget.
     *
     * @return the current budget.
     */
    double getBudget();

    /**
     * Sets the budget to the specified value.
     *
     * @param budget the new budget value.
     */
    void setBudget(double budget);

    /**
     * Increases the budget by the specified amount.
     *
     * @param budget the amount to increase the budget by.
     */
    void increaseBudget(double budget);

    /**
     * Decreases the budget by the specified amount.
     *
     * @param budget the amount to decrease the budget by.
     */
    void decreaseBudget(double budget);
}
