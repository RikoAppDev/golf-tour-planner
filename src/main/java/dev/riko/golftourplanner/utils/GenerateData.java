package dev.riko.golftourplanner.utils;

import dev.riko.golftourplanner.facility.FacilityType;
import dev.riko.golftourplanner.place.Place;

import java.util.*;

import static dev.riko.golftourplanner.facility.FacilityType.*;

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
                    else
                        title = title.concat(String.valueOf(consonants.get(random.nextInt(consonants.size()))));
                }
            }

            float rating = random.nextFloat(0, 10);

            List<FacilityType> facilityList = new ArrayList<>();
            facilityList.add(SHOP);
            if (random.nextBoolean()) facilityList.add(GAS_STATION);
            if (random.nextBoolean()) facilityList.add(HOSTEL);
            if (rating >= 2) if (random.nextBoolean()) facilityList.add(SUPERMARKET);
            if (rating >= 3)
                if (random.nextBoolean()) facilityList.add(RESTAURANTS);
            if (rating >= 6) {
                if (random.nextBoolean()) facilityList.add(HOTEL);
                if (random.nextBoolean()) facilityList.add(SHOPPING_MALL);
            }
            if (rating >= 8) {
                if (random.nextBoolean()) facilityList.add(GOLF_COURSE);
                if (random.nextBoolean()) facilityList.add(AIRPORT);
            }
            float facilityPercentage = facilityList.size() * 100f / FacilityType.values().length;
            int population = 0;
            try {
                population = random.nextInt((int) rating, (int) (facilityPercentage * rating * 1000));
            } catch (IllegalArgumentException e) {
                System.out.println((int) rating + " " + (int) (facilityPercentage * rating * 1000));
            }

            data.add(new Place(lat, lon, title, rating, facilityList, population));
        }

        for (int i = 0; i < data.size(); i++) {
            Place place = data.get(i);
            int distance = 1;
            boolean connect = false;

            while (!connect) {
                for (Place p : data) {
                    if (place != p && p.distanceFrom(place) < distance && !p.getPlaceConnections().contains(place)) {
                        place.addToPlaceConnections(p);
                        p.addToPlaceConnections(place);
                        connect = true;
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
