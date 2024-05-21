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

//TODO fix the API calling

public class Tests {

    private final DecimalFormat df = new DecimalFormat("0.00");

    @Test
    void aerialCalcTest1() {
        // test for aerial calculation from 6221AB to 6221AV
        var calc = new AerialCalculator();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {
            startingLoc = location.getCordsFromPostCode("6221AB");
            endLoc = location.getCordsFromPostCode("6221AV");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.FOOT);
        var calculatedRoute = calc.calculateRoute(route);
        String formattedResult = df.format(calculatedRoute.distanceInKM());

        assertEquals("0,84", formattedResult);
    }

    @Test
    void aerialCalcTest2() {
        // test for aerial calculation between zip code from the API (6229EN) to a zipcode from excel sheet (6219NA)
        var calc = new AerialCalculator();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {
            startingLoc = location.getCordsFromPostCode("6229EN");
            endLoc = location.getCordsFromPostCode("6219NA");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.FOOT);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        df.format(result);

        assertEquals(0, result);
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
