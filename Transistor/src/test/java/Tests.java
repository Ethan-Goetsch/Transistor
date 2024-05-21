import calculators.*;
import entities.*;
import org.apache.poi.hpsf.Decimal;
import org.jetbrains.annotations.TestOnly;
import static entities.RouteType.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import resolvers.LocationResolver;
import utils.PathLocations;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.text.DecimalFormat;

//? 1. Different coordinates. 2. Null coordinates. 3. Non-existent coordinates. 4 same coordinates

public class Tests {

    private final DecimalFormat df = new DecimalFormat("0.00");

    @Test
    void aerialCalcTest1() {
        // test for aerial calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
        var calc = new AerialCalculator();
        Coordinate departure = new Coordinate(50.85523285, 5.692237193);
        Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
        var route = new RouteCalculationRequest(departure, arrival, TransportType.FOOT);
        var resultTrip = calc.calculateRoute(route);
        resultTrip.


        assertEquals("0,84", formattedResult);
    }


    @Test
    void aerialCalcTest2() {
        // test for aerial calculation for null coords
        var calc = new AerialCalculator();
        Coordinate startingLoc = null;
        Coordinate endLoc = null;

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.FOOT);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        df.format(result);

        assertEquals(0, result);
    }

    @Test
    void aerialCalcTest3(){
        // test for aerial calculation for non-existent coordinates


    }

    @Test
    void aerialCalcTest4(){
        // test for aerial calculation for same coordinates
    }

}

class PathCalculatorTests extends PathCalculator {
    private final DecimalFormat df = new DecimalFormat("0.00");

    public PathCalculatorTests() {
        super("src/test/resources/graph");
    }

    @Test
    void pathCalcTest1() {
        // test for bike path calculation from 6221AB to 6221AV both in the Excel sheet
        var calc = new PathCalculatorTests();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {

            startingLoc = location.getCordsFromPostCode("6221AB");
            endLoc = location.getCordsFromPostCode("6221AV");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.BIKE);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        String formattedResult = df.format(result);
        assertEquals("1,44", formattedResult);
    }

    @Test
    void pathCalcTest2(){
        // test for bike path calculation between zip code from the API (6229EN) to a zipcode from Excel sheet (6222XV)
        var calc = new PathCalculatorTests();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {
            startingLoc = location.getCordsFromPostCode("6229EN");
            endLoc = location.getCordsFromPostCode("6222XV");
        } catch (Exception e) {
            e.printStackTrace();
        }
        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.BIKE);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        df.format(result);
        assertEquals(0.00, result);

    }
}
