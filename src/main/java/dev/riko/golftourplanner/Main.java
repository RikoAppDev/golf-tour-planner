package dev.riko.golftourplanner;

import dev.riko.golftourplanner.place.Place;
import dev.riko.golftourplanner.utils.GenerateData;
import dev.riko.golftourplanner.world.World;

import java.util.List;
import java.util.Scanner;

import static dev.riko.golftourplanner.facility.FacilityType.GOLF_COURSE;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount;
        do {
            System.out.print("Amount of places (min 2) >> ");
            amount = scanner.nextInt();
        } while (amount < 2);

        GenerateData generateData = new GenerateData(amount);
        World world = new World(generateData.getData());

        world.showPlaces();
        System.out.println();

        boolean run = true;
        while (run) {
            System.out.print("Place info -> pi | Distance calculator -> dc | EXIT -> e >> ");
            String type = scanner.next();
            switch (type) {
                case "pi" -> {
                    boolean exists;

                    do {
                        System.out.print("Search specific city: ");
                        String city = scanner.next();

                        exists = world.searchCity(city, true);

                        if (!exists) {
                            System.out.println("This city does not exists!");
                            System.out.print("Do you want to list all the cities? | y, n >> ");
                            String showAll = scanner.next();

                            if (showAll.equals("y")) {
                                world.showPlaces();
                                System.out.println();
                            }
                        }
                    } while (!exists);
                }
                case "dc" -> {
                    System.out.print("Select the starting destination (1 - " + amount + "): ");
                    Place startPlace = world.getPlaceList().get(scanner.nextInt() - 1);
                    Place finalPlace;
                    String notGc = "n";

                    do {
                        System.out.print("Select the final destination (1 - " + amount + "): ");
                        finalPlace = world.getPlaceList().get(scanner.nextInt() - 1);
                        if (!finalPlace.getFacilityList().contains(GOLF_COURSE)) {
                            System.out.println("Your final destination does not have golf course!");
                            System.out.print("Would you like to select again? | y, n >> ");
                            notGc = scanner.next();
                        }
                    } while (notGc.equals("y") && !finalPlace.getFacilityList().contains(GOLF_COURSE));
                    System.out.print("How much you want to exploring (0 - 10): ");
                    int exploringRate = scanner.nextInt();

                    System.out.println();
                    System.out.println("Your starting place is:");
                    startPlace.printPlace();
                    System.out.println("Your final destination is:");
                    finalPlace.printPlace();
                    System.out.println();

                    SearchOptimalTrip optimalTrip = new SearchOptimalTrip(startPlace, finalPlace, exploringRate);
                    System.out.println("The air distance from " + startPlace.getTitle() + " to " + finalPlace.getTitle() + " is " + String.format("%.2f", optimalTrip.getOptimalTripLength()) + "km.\n");
                }
                default -> run = false;
            }
        }
    }
}