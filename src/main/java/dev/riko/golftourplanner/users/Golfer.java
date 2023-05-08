package dev.riko.golftourplanner.users;

public class Golfer extends Person implements Budget {
    private double budget;
    private double hcp;
    private String club;

    public Golfer(String firstname, String lastname, int age, double budget, double hcp, String club) {
        super(firstname, lastname, age);
        this.budget = budget;
        this.hcp = hcp;
        this.club = club;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public void increaseBudget(double budget) {

    }

    @Override
    public void decreaseBudget(double budget) {

    }

    public double getHcp() {
        return hcp;
    }

    public void setHcp(double hcp) {
        this.hcp = hcp;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
