package Accessibility;

import entities.AmenityCategory;
import entities.Coordinate;
import entities.geoJson.GeoData;

import java.util.*;

public class IndexCalculator {
    private final static int MAX_TIME = 2 * 60 * 60;

    public List<Double> calculateIndex(List<GeoData> list, Coordinate coordinatePostalCode) {
        Map<AmenityCategory, List<GeoData>> categorizedAmenities = categorizeAmenities(list);

        List<Double> indexes = new ArrayList<>();
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.HEALTHCARE, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.EDUCATION, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.SHOP, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.ENTERTAINMENT, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.TOURISM, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.PUBLIC_SERVICES, new ArrayList<>()), coordinatePostalCode));
        indexes.add(getIndexForCategory(categorizedAmenities.getOrDefault(AmenityCategory.TRANSPORTATION, new ArrayList<>()), coordinatePostalCode));

        return indexes;
    }

    private Map<AmenityCategory, List<GeoData>> categorizeAmenities(List<GeoData> list) {
        Map<AmenityCategory, List<GeoData>> categorizedAmenities = new HashMap<>();
        categorizedAmenities.put(AmenityCategory.HEALTHCARE, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.EDUCATION, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.SHOP, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.ENTERTAINMENT, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.TOURISM, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.PUBLIC_SERVICES, new ArrayList<>());
        categorizedAmenities.put(AmenityCategory.TRANSPORTATION, new ArrayList<>());

        Set<String> healthcare = new HashSet<>(Arrays.asList("nursing_home", "hospital", "clinic", "doctors", "dentist", "veterinary", "pharmacy"));
        Set<String> entertainment = new HashSet<>(Arrays.asList("theatre", "arts_centre", "Cinema", "Nightclub", "casino", "hunting_stand", "restaurant", "cafe", "food_court", "fast_food", "pub", "bar", "ice_cream"));
        Set<String> shopping = new HashSet<>(Arrays.asList("shop", "marketplace", "vending_machine", "photo_booth", "luggage_locker"));
        Set<String> education = new HashSet<>(Arrays.asList("library","public_bookcase","school", "college","university","prep_school","childcare"));
        Set<String> tourism = new HashSet<>(Arrays.asList("tourism"));
        Set<String> publicServices = new HashSet<>(Arrays.asList("police","courthouse","townhall","fire_station","post_office","post_box","atm","bank","bureau_de_change","place_of_worship","community_centre","social_facility","shelter","information","clock","binoculars","sanitary_dump_station","recycling","waste_basket"));
        Set<String> transportation = new HashSet<>(Arrays.asList("fuel", "car_wash", "taxi","bicycle_parking","moped_parking","car_rental","parking_entrance","parking","parking_space","charging_station"));

        for (GeoData geoData : list) {
            if (healthcare.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.HEALTHCARE).add(geoData);
            } else if (entertainment.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.EDUCATION).add(geoData);
            } else if (shopping.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.SHOP).add(geoData);
            } else if (education.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.ENTERTAINMENT).add(geoData);
            } else if (tourism.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.TOURISM).add(geoData);
            } else if (publicServices.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.PUBLIC_SERVICES).add(geoData);
            } else if (transportation.contains(geoData.getType())) {
                categorizedAmenities.get(AmenityCategory.TRANSPORTATION).add(geoData);
            }
        }

        return categorizedAmenities;
    }

    private double getIndexForCategory(List<GeoData> categoryData, Coordinate coordinatePostalCode) {
        Map<String, List<GeoData>> groupedAmenities = new HashMap<>();

        for (GeoData geoData : categoryData) {
            groupedAmenities.computeIfAbsent(geoData.getType(), k -> new ArrayList<>()).add(geoData);
        }

        List<GeoData> nearestLocations = new ArrayList<>();
        for (List<GeoData> amenities : groupedAmenities.values()) {
            GeoData nearest = amenities.stream()
                    .min(Comparator.comparingDouble(geoData -> getDistance(geoData.getLatitude(), geoData.getLongitude(), coordinatePostalCode.getLatitude(), coordinatePostalCode.getLongitude())))
                    .orElse(null);
            if (nearest != null) {
                nearestLocations.add(nearest);
            }
        }

        List<Double> weights = new ArrayList<>();
        List<Integer> times = new ArrayList<>();
        for (GeoData geoData : nearestLocations) {
            // Add weight (example: use predefined weights for different categories)
            weights.add(0.1); // Example weight, replace with actual logic TODO store weights and add the needed ones

            int travelTime = 0; //calculateTravelTime(geoData, coordinatePostalCode); TODO make route requests to get travel times
            times.add(travelTime);
        }
//        return calculator(weights.stream().mapToDouble(Double::doubleValue).toArray(), times.stream().mapToInt(Integer::intValue).toArray());
        return 90;// todo test -> when fixed change
    }

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Convert to kilometers

        return distance;
    }
    private static double calculator(double[] weights, int[]times){
        final double beta = 0.1;
        double[] impedanceValues = calculateImpedanceValue(times, beta);

        //Normalize the weights
        double accessibility = calculateAccessibility(weights, impedanceValues);

        //Find the maximum accessibility for normalization
        double maxAccessibility = calculateMaxAccessibility(weights, beta);
        //Normalize the accessibility
        double normalizedAccessibility = normalizeAccessibility(accessibility, maxAccessibility);
        return normalizedAccessibility;
    }

    private static double normalizeAccessibility(double accessibility, double maxAccessibility) {
        return (accessibility / maxAccessibility) * 100;
    }

    private static double calculateAccessibility(double[] normalizedValues, double[] impedanceValues) {
        double accessibility = 0;
        for (int i = 0; i < normalizedValues.length; i++) {
            accessibility += normalizedValues[i] * impedanceValues[i];
        }
        return accessibility;
    }

    private static double[] calculateImpedanceValue(int[] times, double beta) {
        double[] impendanceValues = new double[times.length];
        for (int i = 0; i < times.length ; i++) {
            impendanceValues[i] = Math.exp(-beta*times[i]);
        }
        return impendanceValues;
    }

    /**
     * The maximum accessibility value serves as a reference point for normalizing the accessibility value.
     * It represents the upper limit of accessibility based on the given weights and the best-case travel time scenario.*/

    private static double calculateMaxAccessibility(double[] normalizedValues, double beta) {
        double maxAccessibility = 0;
        for (int i = 0; i < normalizedValues.length; i++) {
            maxAccessibility += normalizedValues[i] * Math.exp(-beta * 5); // Assume minimum travel time of 5 minute
        }
        return maxAccessibility;
    }
}

