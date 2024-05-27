import calculators.*;
import database.DatabaseManager;
import database.queries.GetAllStopsForTrip;
import database.queries.GetRouteForTripQuery;
import database.queries.GetTripBetweenTwoStopsQuery;
import entities.*;
import entities.transit.TransitNode;
import entities.transit.TransitRoute;
import entities.transit.TransitTrip;
import org.apache.poi.hpsf.Decimal;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.TestOnly;
import static entities.RouteType.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import resolvers.LocationResolver;
import utils.PathLocations;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//? 1. Different coordinates. 2. Null coordinates. 3. Non-existent coordinates. 4 same coordinates

class Tests {

    @BeforeAll
    static void setup() {
        DatabaseManager.getConnection("src/test/resources/credentials.txt");
    }


    private final DecimalFormat df = new DecimalFormat("0.00");
    @Test
    void aerialCalcTest() {
        // test for aerial calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
        var calc = new AerialCalculator();
        Coordinate departure = new Coordinate(50.85523285, 5.692237193);
        Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
        LocalTime departureTime = LocalTime.parse("00:00:00");
        LocalTime arrivalTime = LocalTime.parse("00:00:00");
        var route = new RouteCalculationRequest(departure, arrival,departureTime,arrivalTime, TransportType.FOOT);
        var resultTrip = calc.calculateRoute(route);
        assertNotNull(resultTrip.getArrivalTime());
    }

    @Test
    void pathCalcTest(){
        // test for actual path calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
        var calc = new PathCalculator("src/main/resources/graph");
        Coordinate departure = new Coordinate(50.85523285, 5.692237193);
        Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
        LocalTime departureTime = LocalTime.parse("00:00:00");
        LocalTime arrivalTime = LocalTime.parse("00:00:00");
        var route = new RouteCalculationRequest(departure, arrival,departureTime,arrivalTime, TransportType.FOOT);
        var resultTrip = calc.calculateRoute(route);
        assertNotNull(resultTrip.getArrivalTime());
    }

    @Test
    void busPathTest1() {
        var transitCalculator = new TransitCalculator();
        var trip = transitCalculator.calculateRoute(2578413, 2578366);
        assertNotNull(trip);
        LocalTime expectedArrivalTime = LocalTime.parse("08:32:00");
        assertEquals(expectedArrivalTime, trip.getArrivalTime());
    }


    @Test
    void busPathTest2(){
        var transitCalculator = new TransitCalculator();
        var trip = transitCalculator.calculateRoute(2578413, 2578413);
        assertNull(trip);

    }

    @Test
    void busPathTest3(){
        var transitCalculator = new TransitCalculator();
        var trip = transitCalculator.calculateRoute(999999, 999999);
        assertNull(trip);
    }

}