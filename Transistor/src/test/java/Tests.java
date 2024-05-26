 import calculators.*;
 import entities.*;
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
 import java.sql.SQLOutput;
 import java.text.DecimalFormat;
 import java.time.LocalTime;

 //TODO adjust the assert time values to the expected values

 //? 1. Different coordinates. 2. Null coordinates. 3. Non-existent coordinates. 4 same coordinates

 public class Tests {

     private final DecimalFormat df = new DecimalFormat("0.00");
 //TODO check the final result of the time
     @Test
     void aerialCalcTest1() {
         // test for aerial calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
         var calc = new AerialCalculator();
         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
         Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);

         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }


     @Test
     void aerialCalcTest2() {
         // test for aerial calculation for null coords
         var calc = new AerialCalculator();
         Coordinate departure = null;
         Coordinate arrival = null;
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);

         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }

     @Test
     void aerialCalcTest3(){
         // test for aerial calculation for non-existent coordinates
         var calc = new AerialCalculator();
         Coordinate departure = new Coordinate(-50.85523285,-5.692237193);
         Coordinate arrival = new Coordinate(-50.84027704,-5.68972678);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");

     }

     @Test
     void aerialCalcTest4(){
         // test for aerial calculation for same coordinates
         var calc = new AerialCalculator();
         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
         Coordinate arrival = new Coordinate(50.85523285, 5.692237193);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }

     @Test
     void pathCalcTest1(){
         // test for actual path calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
         var calc = new PathCalculator("src/main/resources/graph");
         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
         Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }

     @Test
     void pathCalcTest2(){
         // test for aerial calculation for null coords
         var calc = new PathCalculator("src/main/resources/graph");
         Coordinate departure = null;
         Coordinate arrival = null;
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }

     @Test
     void pathCalcTest3(){
         // test for aerial calculation for non-existent coordinates
         var calc = new PathCalculator("src/main/resources/graph");
         Coordinate departure = new Coordinate(-50.85523285, -5.692237193);
         Coordinate arrival = new Coordinate(-50.84027704, -5.68972678);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");

     }

     @Test
     void pathCalcTest4(){
         // test for aerial calculation for same coordinates
         var calc = new PathCalculator("src/main/resources/graph");
         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
         Coordinate arrival = new Coordinate(50.85523285, 5.692237193);
         LocalTime departureTime = LocalTime.parse("00:00:00");
         var route = new RouteCalculationRequest(departure, arrival,departureTime,departureTime,TransportType.FOOT);
         var resultTrip = calc.calculateRoute(route);
         assertEquals(resultTrip.getArrivalDescription(), "00:00:00");
     }


 }


