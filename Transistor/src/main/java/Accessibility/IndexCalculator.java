package Accessibility;

import calculators.PathCalculator;
import calculators.TransitCalculator;
import entities.*;
import entities.geoJson.GeoData;
import entities.geoJson.GeoDeserializer;
import utils.PathLocations;

import java.time.LocalTime;
import java.util.*;

public class IndexCalculator {

    private static final Map<String, Double> amenityWeights = new HashMap<>();
    static {
        amenityWeights.put("hospital", 1.0);
        amenityWeights.put("clinic", 0.8);
        amenityWeights.put("nursing_home", 0.7);
        amenityWeights.put("doctors", 0.6);
        amenityWeights.put("dentist", 0.5);
        amenityWeights.put("veterinary", 0.4);
        amenityWeights.put("pharmacy", 0.9);
        amenityWeights.put("theatre", 0.8);
        amenityWeights.put("arts_centre", 0.7);
        amenityWeights.put("cinema", 0.9);
        amenityWeights.put("nightclub", 0.6);
        amenityWeights.put("casino", 0.5);
        amenityWeights.put("restaurant", 0.9);
        amenityWeights.put("cafe", 0.8);
        amenityWeights.put("food_court", 0.7);
        amenityWeights.put("fast_food", 0.6);
        amenityWeights.put("pub", 0.5);
        amenityWeights.put("bar", 0.4);
        amenityWeights.put("ice_cream", 0.3);
        amenityWeights.put("shop", 0.9);
        amenityWeights.put("hunting_stand", 0.6);
        amenityWeights.put("marketplace", 0.8);
        amenityWeights.put("vending_machine", 0.3);
        amenityWeights.put("photo_booth", 0.2);
        amenityWeights.put("luggage_locker", 0.4);
        amenityWeights.put("library", 0.9);
        amenityWeights.put("public_bookcase", 0.3);
        amenityWeights.put("school", 1.0);
        amenityWeights.put("college", 0.9);
        amenityWeights.put("university", 1.0);
        amenityWeights.put("prep_school", 0.8);
        amenityWeights.put("childcare", 0.7);
        amenityWeights.put("tourism", 0.6);
        amenityWeights.put("police", 1.0);
        amenityWeights.put("courthouse", 0.8);
        amenityWeights.put("townhall", 0.7);
        amenityWeights.put("fire_station", 0.9);
        amenityWeights.put("post_office", 0.8);
        amenityWeights.put("post_box", 0.4);
        amenityWeights.put("atm", 0.7);
        amenityWeights.put("bank", 0.9);
        amenityWeights.put("bureau_de_change", 0.6);
        amenityWeights.put("place_of_worship", 0.5);
        amenityWeights.put("community_centre", 0.6);
        amenityWeights.put("social_facility", 0.7);
        amenityWeights.put("shelter", 0.5);
        amenityWeights.put("information", 0.4);
        amenityWeights.put("clock", 0.3);
        amenityWeights.put("binoculars", 0.2);
        amenityWeights.put("sanitary_dump_station", 0.1);
        amenityWeights.put("recycling", 0.4);
        amenityWeights.put("waste_basket", 0.2);
        amenityWeights.put("fuel", 0.9);
        amenityWeights.put("car_wash", 0.6);
        amenityWeights.put("taxi", 0.7);
        amenityWeights.put("bicycle_parking", 0.5);
        amenityWeights.put("moped_parking", 0.4);
        amenityWeights.put("car_rental", 0.8);
        amenityWeights.put("parking_entrance", 0.5);
        amenityWeights.put("parking", 0.7);
        amenityWeights.put("parking_space", 0.6);
        amenityWeights.put("charging_station", 0.8);
    }
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

        double[] weights = new double[nearestLocations.size()];
        int[] times = new int[nearestLocations.size()];
        int i = 0;
        for (GeoData geoData : nearestLocations) {
            // Add weight (example: use predefined weights for different categories)
            // Example weight, replace with actual logic TODO store weights and add the needed ones
            double weight = amenityWeights.containsKey(geoData.getType()) ? amenityWeights.get(geoData.getType()) : 0.0;
            weights[i] = weight;
            int travelTime = 0; //calculateTravelTime(geoData, coordinatePostalCode); TODO make route requests to get travel times

            RouteCalculationRequest request = new RouteCalculationRequest(new Coordinate(geoData.getLatitude(), geoData.getLongitude()), coordinatePostalCode, LocalTime.parse("00:00:00"), LocalTime.parse("15:28:00"), TransportType.BUS);
            PathCalculator calc = new PathCalculator(PathLocations.GRAPH_RESOURCE_FOLDER);
            Trip trip = calc.calculateRoute(request);
            travelTime = (int) trip.getTravelTime();

            times[i] = travelTime;
            i++;
        }
        //return calculator(weights.stream().mapToDouble(Double::doubleValue).toArray(), times.stream().mapToInt(Integer::intValue).toArray());
        //return 90;// todo test -> when fixed change
        return (int) Math.round(calculator(weights, times));

    }

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
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

    public static void main(String[] args) {
        IndexCalculator indexCalculator = new IndexCalculator();
        List<GeoData> list = new ArrayList<>();
        GeoDeserializer geoDeserializer = new GeoDeserializer();
        try {
            list = geoDeserializer.deserializeAllGeoData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Coordinate code = new Coordinate(50.82092283, 5.708232104);
        System.out.println(indexCalculator.getIndexForCategory(list, code));
    }
}

