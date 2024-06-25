import accessibility.IndexCalculator;
import application.ApplicationManager;
import application.RequestValidator;
import calculators.*;
import database.DatabaseManager;
import entities.Coordinate;
import entities.TransportType;
import entities.Trip;
import entities.geoJson.GeoDeserializer;
import org.junit.jupiter.api.Test;
import resolvers.LocationResolver;
import ui.UIController;
import utils.PathLocations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


public class IndexCalculatorTest {
    @Test
    void indexCalcTest() throws IOException {
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        LocationResolver locationResolver = new LocationResolver(PathLocations.MASS_LOCATION_FILE);
        RequestValidator requestValidator = new RequestValidator();

        var accessibilityCalculator = new IndexCalculator();
        TransitGraphCalculator transitGraphCalculator = new TransitGraphCalculator();

        var transitCalculators = new ArrayList<TransitCalculator>();
        transitCalculators.add(new DirectTransitCalculator());
        transitCalculators.add(new TransferTransitCalculator(transitGraphCalculator));

        AerialCalculator aerialCalculator = new AerialCalculator();
        GeoDeserializer geoDeserializer = new GeoDeserializer();

        ApplicationManager manager = new ApplicationManager(locationResolver, requestValidator, accessibilityCalculator, transitCalculators, aerialCalculator);
        accessibilityCalculator.setManager(manager);
        var allGeoData = GeoDeserializer.deserializeAllGeoData("src/test/resources/geoJson/amenity.geojson", "src/test/resources/geoJson/shop.geojson","src/test/resources/geoJson/tourism.geojson");
        var indexes = accessibilityCalculator.calculateIndex(allGeoData, new Coordinate(50.84656724, 5.690763605), false, 1, TransportType.BUS );
        assertFalse(indexes.isEmpty());
        assertEquals(7, indexes.size());
        assertEquals(88, (int)Math.round(indexes.get(0)));
        assertEquals(96, (int)Math.round(indexes.get(1)));
        assertEquals(93, (int)Math.round(indexes.get(2)));
        assertEquals(98, (int)Math.round(indexes.get(3)));
        assertEquals(99, (int)Math.round(indexes.get(4)));
        assertEquals(94, (int)Math.round(indexes.get(5)));
        assertEquals(96,(int)Math.round(indexes.get(6)));

    }

    @Test
    void geoDeserializingTest() throws IOException {

        var allGeoData = GeoDeserializer.deserializeAllGeoData("src/test/resources/geoJson/amenity.geojson", "src/test/resources/geoJson/shop.geojson","src/test/resources/geoJson/tourism.geojson");
        assertFalse(allGeoData.isEmpty());
        assertEquals(1577, allGeoData.size());
    }
}
