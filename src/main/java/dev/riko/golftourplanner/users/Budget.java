package dev.riko.golftourplanner.users;

public interface Budget {
    double getBudget();

    void setBudget(double budget);

    void increaseBudget(double budget);

    void decreaseBudget(double budget);
}
