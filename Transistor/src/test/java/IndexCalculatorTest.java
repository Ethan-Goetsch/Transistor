import accessibility.IndexCalculator;
import application.ApplicationManager;
import application.RequestValidator;
import calculators.AerialCalculator;
import calculators.TransitCalculator;
import entities.Coordinate;
import entities.TransportType;
import entities.Trip;
import entities.geoJson.GeoDeserializer;
import org.junit.jupiter.api.Test;
import resolvers.LocationResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class IndexCalculatorTest {
    @Test
    void indexCalcTest() throws IOException {

    }

    @Test
    void geoDeserializingTest() throws IOException {

        var allGeoData = GeoDeserializer.deserializeAllGeoData("src/test/resources/geoJson/amenity.geojson", "src/test/resources/geoJson/shop.geojson","src/test/resources/geoJson/tourism.geojson");
        assertFalse(allGeoData.isEmpty());
        assertEquals(1577, allGeoData.size());
    }
}
