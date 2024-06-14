package entities.geoJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.*;

public class GeoDeserializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> readGeoFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), Map.class);
    }

    public static List<GeoData> deserializeGeoData(String filePath, String type) throws IOException {
        List<GeoData> result = new ArrayList<>();

        Map<String, Object> geoDataMap = readGeoFile(filePath);
        List<Map<String, Object>> features = (List<Map<String, Object>>) geoDataMap.get("features");

        for (Map<String, Object> feature : features) {
            Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
            List<Double> coordinates = (List<Double>) geometry.get("coordinates");
            String exactType = type;
            if("amenity".equals(type)){
                exactType = (String) ((Map<String, Object>) feature.get("properties")).get("amenity");
            }
            // in geojson files the coordinates start with longitude and then latitude
            GeoData geoData = new GeoData(exactType, coordinates.get(1), coordinates.get(0));
            result.add(geoData);
        }

        return result;
    }

    public static List<GeoData> deserializeAllGeoData() throws IOException {
        List<GeoData> allGeoData = new ArrayList<>();
        allGeoData.addAll(deserializeGeoData("Transistor/src/main/resources/geoJson/amenity.geojson", "amenity"));
        allGeoData.addAll(deserializeGeoData("Transistor/src/main/resources/geoJson/shop.geojson", "shop"));
        allGeoData.addAll(deserializeGeoData("Transistor/src/main/resources/geoJson/tourism.geojson", "tourism"));
        return allGeoData;
    }

public static void main(String[] args) throws IOException {
        List<GeoData> allGeoData = deserializeAllGeoData();
        for (GeoData geoData : allGeoData) {
            System.out.println(geoData.getType() + " " + geoData.getLatitude() + " " + geoData.getLongitude());
        }
    }
}
