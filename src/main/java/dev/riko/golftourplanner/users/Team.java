package dev.riko.golftourplanner.users;

import dev.riko.golftourplanner.interfaces.Budget;

import java.util.ArrayList;
import java.util.List;

public class Team implements Budget {
    private String name;
    private double budget;
    private List<Golfer> golfers = new ArrayList<>();

    public Team(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGolfer(Golfer golfer) {
        golfers.add(golfer);
    }

    public List<Golfer> getGolfers() {
        return golfers;
    }

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
