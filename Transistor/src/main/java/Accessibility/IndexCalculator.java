package accessibility;

import application.ApplicationManager;
import entities.*;
import entities.geoJson.GeoData;
import entities.geoJson.GeoDeserializer;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class IndexCalculator {

    private ApplicationManager manager;
    private boolean disabledPersonSetting;

    public IndexCalculator(){
        disabledPersonSetting = false;
    }

    public void setManager(ApplicationManager manager){
        this.manager = manager;
    }

    private static final Map<String, Double[]> amenityWeights = new HashMap<>();
    static {
        //
        amenityWeights.put("hospital", new Double[]{1.0, 1.0});
        amenityWeights.put("clinic", new Double[]{0.5, 1.0});
        amenityWeights.put("nursing_home", new Double[]{0.2, 0.9});
        amenityWeights.put("doctors", new Double[]{0.6, 1.0});
        amenityWeights.put("dentist", new Double[]{0.5, 0.9});
        amenityWeights.put("veterinary", new Double[]{0.6, 0.6});
        amenityWeights.put("pharmacy", new Double[]{0.4, 0.9});
        amenityWeights.put("theatre", new Double[]{0.5, 0.5});
        amenityWeights.put("arts_centre", new Double[]{0.1, 0.5});
        amenityWeights.put("cinema", new Double[]{0.3, 0.3});
        amenityWeights.put("nightclub", new Double[]{0.5, 0.2});
        amenityWeights.put("casino", new Double[]{0.2, 0.5});
        amenityWeights.put("restaurant", new Double[]{0.7, 0.6});
        amenityWeights.put("cafe", new Double[]{0.7, 0.3});
        amenityWeights.put("food_court", new Double[]{0.7, 0.7});
        amenityWeights.put("fast_food", new Double[]{0.7, 0.6});
        amenityWeights.put("pub", new Double[]{0.6, 0.4});
        amenityWeights.put("bar", new Double[]{0.6, 0.4});
        amenityWeights.put("ice_cream", new Double[]{0.4, 0.6});
        amenityWeights.put("shop", new Double[]{0.9, 0.7});
        amenityWeights.put("hunting_stand", new Double[]{0.1, 0.1});
        amenityWeights.put("marketplace", new Double[]{0.6, 0.5});
        amenityWeights.put("vending_machine", new Double[]{0.1, 0.1});
        amenityWeights.put("photo_booth", new Double[]{0.1, 0.1});
        amenityWeights.put("luggage_locker", new Double[]{0.1, 0.1});
        amenityWeights.put("library", new Double[]{0.4, 0.7});
        amenityWeights.put("public_bookcase", new Double[]{0.2, 0.3});
        amenityWeights.put("school", new Double[]{1.0, 0.8});
        amenityWeights.put("college", new Double[]{0.9, 0.8});
        amenityWeights.put("university", new Double[]{1.0, 0.8});
        amenityWeights.put("prep_school", new Double[]{0.8, 0.8});
        amenityWeights.put("childcare", new Double[]{0.6, 0.6});
        amenityWeights.put("tourism", new Double[]{0.4, 0.2});
        amenityWeights.put("police", new Double[]{0.5, 0.8});
        amenityWeights.put("courthouse", new Double[]{0.1, 0.1});
        amenityWeights.put("townhall", new Double[]{0.2, 0.7});
        amenityWeights.put("fire_station", new Double[]{0.9, 0.8});
        amenityWeights.put("post_office", new Double[]{0.4, 0.8});
        amenityWeights.put("post_box", new Double[]{0.4, 0.8});
        amenityWeights.put("atm", new Double[]{0.2, 0.3});
        amenityWeights.put("bank", new Double[]{0.2, 0.4});
        amenityWeights.put("bureau_de_change", new Double[]{0.1, 0.1});
        amenityWeights.put("place_of_worship", new Double[]{0.3, 0.5});
        amenityWeights.put("community_centre", new Double[]{0.4, 0.7});
        amenityWeights.put("social_facility", new Double[]{0.4, 0.7});
        amenityWeights.put("shelter", new Double[]{0.3, 0.5});
        amenityWeights.put("information", new Double[]{0.1, 0.2});
        amenityWeights.put("clock", new Double[]{0.1, 0.1});
        amenityWeights.put("binoculars", new Double[]{0.1, 0.1});
        amenityWeights.put("sanitary_dump_station", new Double[]{0.1, 0.1});
        amenityWeights.put("recycling", new Double[]{0.8, 0.2});
        amenityWeights.put("waste_basket", new Double[]{0.2, 0.2});
        amenityWeights.put("fuel", new Double[]{0.7, 0.3});
        amenityWeights.put("car_wash", new Double[]{0.2, 0.1});
        amenityWeights.put("taxi", new Double[]{0.2, 0.7});
        amenityWeights.put("bicycle_parking", new Double[]{0.5, 0.6});
        amenityWeights.put("moped_parking", new Double[]{0.2, 0.2});
        amenityWeights.put("car_rental", new Double[]{0.2, 0.3});
        amenityWeights.put("parking_entrance", new Double[]{0.6, 0.6});
        amenityWeights.put("parking", new Double[]{1.0, 0.1});
        amenityWeights.put("parking_space", new Double[]{0.8, 0.1});
        amenityWeights.put("charging_station", new Double[]{0.4, 0.3});
        amenityWeights.put("apartment", new Double[]{0.6, 0.1});
        amenityWeights.put("artwork", new Double[]{0.1, 0.1});
        amenityWeights.put("attraction", new Double[]{0.2, 0.1});
        amenityWeights.put("caravan_site", new Double[]{0.1, 0.1});
        amenityWeights.put("gallery", new Double[]{0.1, 0.1});
        amenityWeights.put("guest_house", new Double[]{0.2, 0.3});
        amenityWeights.put("hostel", new Double[]{0.1, 0.1});
        amenityWeights.put("hotel", new Double[]{0.1, 0.1});
        amenityWeights.put("museum", new Double[]{0.1, 0.2});
        amenityWeights.put("viewpoint", new Double[]{0.1, 0.1});
        amenityWeights.put("zoo", new Double[]{0.1, 0.3});
        amenityWeights.put("supermarket", new Double[]{0.5, 0.7});
        amenityWeights.put("greengrocer", new Double[]{0.5, 0.7});
        amenityWeights.put("bakery", new Double[]{0.3, 0.6});
        amenityWeights.put("butcher", new Double[]{0.3, 0.6});
        amenityWeights.put("medical_supply", new Double[]{0.3, 0.8});
        amenityWeights.put("convenience", new Double[]{0.1, 0.5});
        amenityWeights.put("hairdresser", new Double[]{0.3, 0.3});
        amenityWeights.put("nutrition_supplements", new Double[]{0.1, 0.1});
        amenityWeights.put("optician", new Double[]{0.1, 0.2});
        amenityWeights.put("books", new Double[]{0.2, 0.2});
        amenityWeights.put("car_parts", new Double[]{0.4, 0.2});
        amenityWeights.put("car", new Double[]{0.1, 0.2});
        amenityWeights.put("sports", new Double[]{0.5, 0.6});
        amenityWeights.put("massage", new Double[]{0.1, 0.3});
        amenityWeights.put("storage_rental", new Double[]{0.1, 0.1});
        amenityWeights.put("hearing_aids", new Double[]{0.1, 0.9});
        amenityWeights.put("outdoor", new Double[]{0.3, 0.8});
        amenityWeights.put("soft_drugs", new Double[]{0.2, 0.1});
        amenityWeights.put("dry_cleaning", new Double[]{0.2, 0.3});
        amenityWeights.put("market", new Double[]{0.2, 0.3});
    }


    public List<Double> calculateIndex(List<GeoData> list, Coordinate coordinatePostalCode, boolean disabledPersonSetting) {
        this.disabledPersonSetting = disabledPersonSetting;
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
        Set<String> entertainment = new HashSet<>(Arrays.asList("theatre", "arts_centre", "Cinema", "Nightclub", "casino", "hunting_stand", "restaurant",
                "cafe", "food_court", "fast_food", "pub", "bar", "ice_cream"));
        Set<String> shopping = new HashSet<>(Arrays.asList(
                "marketplace", "vending_machine", "photo_booth", "luggage_locker",
                "supermarket", "greengrocer", "bakery", "butcher", "medical_supply", "convenience",
                "hairdresser", "nutrition_supplements", "optician", "books", "car_parts", "car",
                "sports", "massage",
                "storage_rental", "hearing_aids", "outdoor", "soft_drugs", "dry_cleaning", "market"
        ));//3,4
        Set<String> education = new HashSet<>(Arrays.asList("library","public_bookcase","school", "college","university","prep_school","childcare"));
        Set<String> tourism = new HashSet<>(Arrays.asList("apartment", "artwork", "attraction", "caravan_site", "gallery","guest_house",
                "hostel", "hotel", "information", "museum", "viewpoint", "zoo"));
        Set<String> publicServices = new HashSet<>(Arrays.asList("police","courthouse","townhall","fire_station","post_office","post_box","atm","bank",
                "bureau_de_change","place_of_worship","community_centre","social_facility","shelter","information","clock","binoculars",
                "sanitary_dump_station","recycling","waste_basket"));//8
        Set<String> transportation = new HashSet<>(Arrays.asList("fuel", "car_wash", "taxi","bicycle_parking","moped_parking","car_rental",
                "parking_entrance","parking","parking_space","charging_station"));

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
            Double[] weight = amenityWeights.get(geoData.getType());
            if(disabledPersonSetting){
                weights[i] = weight[1];
            }else{
                weights[i] = weight[0];
            }

            int travelTime = 15;  // Default travel time in minutes
            Journey journeyToAmenity = manager.getJourney(coordinatePostalCode, new Coordinate(geoData.getLatitude(), geoData.getLongitude()), new RouteRequest("", "", TransportType.BUS, RouteType.ACTUAL));
            if (journeyToAmenity != null) {
                try {
                    LocalTime departureTime = journeyToAmenity.getTrips().getFirst().getDepartureTime();
                    LocalTime arrivalTime = journeyToAmenity.getTrips().getLast().getArrivalTime();
                    // Calculate travel time in minutes
                    travelTime = (int) Duration.between(departureTime, arrivalTime).toMinutes();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            times[i] = travelTime;
            i++;
        }

        return Math.round(calculator(weights, times));
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
    private static double calculator(double[] weights, int[] times) {
        final double beta = 0.1;
        double[] impedanceValues = calculateImpedanceValue(times, beta);

        double accessibility = calculateAccessibility(weights, impedanceValues);

        double maxAccessibility = calculateMaxAccessibility(weights, beta);

        return normalizeAccessibility(accessibility, maxAccessibility);
    }

    private static double normalizeAccessibility(double accessibility, double maxAccessibility) {
        double normalizedAccessibility = (accessibility / maxAccessibility) * 100;
        return Math.max(0, normalizedAccessibility);
    }

    private static double calculateAccessibility(double[] normalizedValues, double[] impedanceValues) {
        double accessibility = 0;
        for (int i = 0; i < normalizedValues.length; i++) {
            accessibility += normalizedValues[i] * impedanceValues[i];
        }
        return accessibility;
    }

    private static double[] calculateImpedanceValue(int[] times, double beta) {
        double[] impedanceValues = new double[times.length];
        for (int i = 0; i < times.length; i++) {
            impedanceValues[i] = Math.exp(-beta * times[i]);
        }
        return impedanceValues;
    }

    private static double calculateMaxAccessibility(double[] normalizedValues, double beta) {
        double maxAccessibility = 0;
        for (int i = 0; i < normalizedValues.length; i++) {
            maxAccessibility += normalizedValues[i] * Math.exp(-beta * 1); // Assume minimum travel time of 1 minutes
        }
        return maxAccessibility;
    }

}