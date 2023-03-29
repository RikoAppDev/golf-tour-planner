package dev.riko.golftourplanner.utils;

import dev.riko.golftourplanner.place.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

            data.add(new Place(lat, lon, title, rating));
        }
        return data;
    }

    public List<Place> getData() {
        return placesData;
    }
}
