package dev.riko.golftourplanner;

import dev.riko.golftourplanner.pathfinding.SearchOptimalTrip;
import dev.riko.golftourplanner.world.place.Place;
import dev.riko.golftourplanner.utils.GenerateData;
import dev.riko.golftourplanner.world.World;

import java.util.List;
import java.util.Scanner;

import static dev.riko.golftourplanner.world.facility.FacilityType.GOLF_COURSE;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount;
        do {
            System.out.print("Amount of places (min 5) >> ");
            amount = scanner.nextInt();
        } while (amount < 5);

        GenerateData generateData = new GenerateData(amount);
        World world = World.getInstance();
        world.setPlaceList(generateData.getData());
        world.showPlaces();
        System.out.println();

        boolean run = true;
        while (run) {
            System.out.print("List places -> lp | Place info -> pi | Distance calculator -> dc | EXIT -> e >> ");
            String type = scanner.next();
            switch (type) {
                case "lp" -> {
                    world.showPlaces();
                    System.out.println();
                }
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
                    Place startPlace = null;
                    Place finalPlace = null;
                    boolean existsStartPlace;
                    boolean existsFinalPlace;

                    do {
                        System.out.print("Select the start destination: ");
                        String city = scanner.next();
                        existsStartPlace = world.searchCity(city);

                        if (existsStartPlace) {
                            List<Place> startPlaces = world.getPlaces(city);
                            if (startPlaces.size() == 1) {
                                startPlace = startPlaces.get(0);
                            } else {
                                int input;
                                for (int i = 0; i < startPlaces.size(); i++) {
                                    System.out.print((i + 1) + ". ");
                                    System.out.println(startPlaces.get(i).placeInfo());
                                }
                                System.out.print("There is more possible start destinations, select one of them (1 - " + startPlaces.size() + ") >> ");

                                do {
                                    input = scanner.nextInt();
                                    if (input > 0 && input <= startPlaces.size()) {
                                        startPlace = startPlaces.get(input - 1);
                                        System.out.print("Selected start destination: ");
                                        System.out.println(startPlace.placeInfo());
                                    } else {
                                        System.out.print("Wrong input! Select again >> ");
                                    }
                                } while (input <= 0 || input > startPlaces.size());
                            }
                        } else {
                            System.out.println("This city does not exists!");
                            System.out.print("Do you want to list all the cities? | y, n >> ");
                            String showAll = scanner.next();

                            if (showAll.equals("y")) {
                                world.showPlaces();
                                System.out.println();
                            }
                        }
                    } while (!existsStartPlace);

                    String notGcInput = "n";
                    do {
                        System.out.print("Select the final destination: ");
                        String city = scanner.next();
                        existsFinalPlace = world.searchCity(city);

                        if (existsFinalPlace) {
                            List<Place> finalPlaces = world.getPlaces(city);
                            if (finalPlaces.size() == 1) {
                                finalPlace = finalPlaces.get(0);

                                if (!finalPlace.hasFacility(GOLF_COURSE)) {
                                    System.out.println("Your final destination does not have golf course!");
                                    System.out.print("Do you want to list all the cities with golf course? | y, n >> ");
                                    String showGolfCourse = scanner.next();

                                    if (showGolfCourse.equals("y")) {
                                        world.showPlacesWithFacility(GOLF_COURSE);
                                    }

                                    System.out.print("Would you like to select again? | y, n >> ");
                                    notGcInput = scanner.next();
                                }
                            } else {
                                int input;
                                for (int i = 0; i < finalPlaces.size(); i++) {
                                    System.out.print((i + 1) + ". ");
                                    System.out.println(finalPlaces.get(i).placeInfo());
                                }
                                System.out.print("There is more possible final destinations, select one of them (1 - " + finalPlaces.size() + ") >> ");

                                do {
                                    input = scanner.nextInt();
                                    if (input > 0 && input <= finalPlaces.size()) {
                                        finalPlace = finalPlaces.get(input - 1);
                                        System.out.print("Selected final destination: ");
                                        System.out.println(finalPlace.placeInfo());

                                        if (!finalPlace.hasFacility(GOLF_COURSE)) {
                                            System.out.println("Your final destination does not have golf course!");
                                            System.out.print("Do you want to list all the cities with golf course? | y, n >> ");
                                            String showGolfCourse = scanner.next();

                                            if (showGolfCourse.equals("y")) {
                                                world.showPlacesWithFacility(GOLF_COURSE);
                                            }

                                            System.out.print("Would you like to select again? | y, n >> ");
                                            notGcInput = scanner.next();
                                        }
                                    } else {
                                        System.out.print("Wrong input! Select again >> ");
                                    }
                                } while (input <= 0 || input > finalPlaces.size());
                            }
                        } else {
                            System.out.println("This city does not exists!");
                            System.out.print("Do you want to list all the cities? | y, n >> ");
                            String showAll = scanner.next();

                            if (showAll.equals("y")) {
                                world.showPlaces();
                                System.out.println();
                            }
                        }
                    } while (!existsFinalPlace || (notGcInput.equals("y") && !finalPlace.hasFacility(GOLF_COURSE)));

                    System.out.print("How much you want to exploring (0 - 10): ");
                    int exploringRate = scanner.nextInt();

                    System.out.println();
                    System.out.println("Your starting place is:");
                    System.out.println(startPlace.placeInfo());
                    System.out.println("Your final destination is:");
                    System.out.println(finalPlace.placeInfo());
                    System.out.println();

                    SearchOptimalTrip optimalTrip = new SearchOptimalTrip(world, startPlace, finalPlace, exploringRate);
                    System.out.println("Air distance from " + startPlace.getTitle() + " to " + finalPlace.getTitle() + " is " + String.format("%.2f", optimalTrip.getAirDistanceLength()) + "km.");
                    System.out.println("The shortest route between places is: " + optimalTrip.printShortestPath());
                    System.out.println("Length of the route: " + String.format("%.2f", optimalTrip.getShortestPathLength()) + "km");
                    System.out.println();
                }
                default -> run = false;
            }
        }
    }
}