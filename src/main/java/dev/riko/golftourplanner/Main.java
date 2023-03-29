package dev.riko.golftourplanner;

import dev.riko.golftourplanner.place.Place;
import dev.riko.golftourplanner.utils.GenerateData;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Amount of places >> ");
        int amount = scanner.nextInt();

        GenerateData generateData = new GenerateData(amount);
        List<Place> placeList = generateData.getData();

        for (int i = 0; i < placeList.size(); i++) {
            Place place = placeList.get(i);
            System.out.print((i + 1) + ". ");
            place.printPlace();
        }
        System.out.println();

        System.out.print("Select the starting destination (1 - " + amount + "): ");
        Place startPlace = placeList.get(scanner.nextInt() - 1);
        System.out.print("Select the final destination (1 - " + amount + "): ");
        Place finalPlace = placeList.get(scanner.nextInt() - 1);
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
}