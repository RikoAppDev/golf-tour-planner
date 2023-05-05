package dev.riko.golftourplanner.utils;

import dev.riko.golftourplanner.world.facility.Facility;
import dev.riko.golftourplanner.world.facility.FacilityType;
import dev.riko.golftourplanner.world.place.Place;

import java.util.*;

import static dev.riko.golftourplanner.world.facility.FacilityType.*;

public class GenerateData {
    private final List<Place> placesData;
    private final Random random = new Random(System.currentTimeMillis());
    private final static List<String> vowels = new ArrayList<>(Arrays.asList("a", "e", "i", "o", "u"));
    private final static List<String> consonants = new ArrayList<>(Arrays.asList("b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"));

    public GenerateData(int amount) {
        this.placesData = generateData(amount);
    }

    private List<Place> generateData(int amount) {
        List<Place> data = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double lat = random.nextDouble(0, 100);
            double lon = random.nextDouble(0, 100);

            String title = String.valueOf((char) random.nextInt(65, 91));
            int titleLength = random.nextInt(1, 10);

            for (int j = 0; j < titleLength; j++) {
                String prev = String.valueOf(title.charAt(j)).toLowerCase();
                if (vowels.contains(prev)) {
                    if (random.nextInt(101) < 91) {
                        title = title.concat(String.valueOf(consonants.get(random.nextInt(consonants.size()))));
                    } else {
                        title = title.concat(String.valueOf(vowels.get(random.nextInt(vowels.size()))));
                    }
                } else {
                    if (random.nextInt(101) < 91)
                        title = title.concat(String.valueOf(vowels.get(random.nextInt(vowels.size()))));
                    else title = title.concat(String.valueOf(consonants.get(random.nextInt(consonants.size()))));
                }
            }

            float rating = random.nextFloat(0, 10);

            List<Facility> facilityList = new ArrayList<>();
            facilityList.add(new Facility(SHOP));
            if (random.nextBoolean()) facilityList.add(new Facility(GAS_STATION));
            if (random.nextBoolean()) facilityList.add(new Facility(HOSTEL));
            if (rating >= 2) if (random.nextBoolean()) facilityList.add(new Facility(SUPERMARKET));
            if (rating >= 3) if (random.nextBoolean()) facilityList.add(new Facility(RESTAURANTS));
            if (rating >= 6) {
                if (random.nextBoolean()) facilityList.add(new Facility(HOTEL));
                if (random.nextBoolean()) facilityList.add(new Facility(SHOPPING_MALL));
            }
            if (rating >= 8) {
                if (random.nextBoolean()) facilityList.add(new Facility(GOLF_COURSE));
                if (random.nextBoolean()) facilityList.add(new Facility(AIRPORT));
            }
            float facilityPercentage = facilityList.size() * 100f / FacilityType.values().length;
            int population = 0;
            try {
                population = random.nextInt((int) rating, (int) (facilityPercentage * rating * 1000));
            } catch (IllegalArgumentException e) {
                System.out.println((int) rating + " " + (int) (facilityPercentage * rating * 1000));
            }

            int roadsCount = (int) Math.ceil(rating / 1.5);
            if (roadsCount < 3) roadsCount = 3;

            data.add(new Place(lat, lon, title, rating, facilityList, population, roadsCount));
        }


        for (Place place : data) {
            int distance = 1;

            while (place.getPlaceConnections().size() < place.getRoadsCount()) {
                for (Place potentialConnection : data) {
                    if (place != potentialConnection && potentialConnection.distanceFrom(place) <= distance && !place.getPlaceConnections().contains(potentialConnection)) {
                        place.addToPlaceConnections(potentialConnection);
                        break;
                    }
                }
                distance++;
            }
        }

        return data;
    }

    public List<Place> getData() {
        return placesData;
    }
}
