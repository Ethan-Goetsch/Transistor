import calculators.*;
import entities.*;
import org.apache.poi.hpsf.Decimal;
import org.jetbrains.annotations.TestOnly;
import static entities.RouteType.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resolvers.LocationResolver;
import utils.PathLocations;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.text.DecimalFormat;

class Tests {

    private final DecimalFormat df = new DecimalFormat("0.00");

//    @BeforeAll
//    private void setUp()
//    {
//
//    }
//
//    @BeforeEach
//    //!logic before each test
    
    @Test
    void aerialCalcTest1(){
        //test for aerial calculation from 6221AB to 6221AV
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
        double result = calculatedRoute.distanceInKM();
        df.format(result);


        assertEquals(0.844, result);
    }

    @Test
    void aerialCalcTest2(){
        //test for aerial calculation from 
        var calc = new AerialCalculator();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {
            startingLoc = location.getCordsFromPostCode("xxx");
            endLoc = location.getCordsFromPostCode("xxxx");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.FOOT);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        df.format(result);


        assertEquals(0.844, result);
    }


    }

    @Test
    void aerialCalcTest3(){
        //test for aerial calculation from a non-existing postal code to a Maastricht postal code 6211AL
        var calc = new AerialCalculator();
        var location = new LocationResolver("src/main/resources/MassZipLatLon.xlsx");
        Coordinate startingLoc = null;
        Coordinate endLoc = null;
        try {
            startingLoc = location.getCordsFromPostCode("9999YY");
            endLoc = location.getCordsFromPostCode("6211AL");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var route = new RouteCalculationRequest(startingLoc, endLoc, TransportType.FOOT);
        var calculatedRoute = calc.calculateRoute(route);
        double result = calculatedRoute.distanceInKM();
        df.format(result);


        assertEquals(0, result);
    }





    /*@Test
    void pathCalcTest1(){

        var calculator = new PathCalculator();

    }

     */

    
}
