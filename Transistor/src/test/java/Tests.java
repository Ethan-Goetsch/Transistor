// import calculators.*;
// import database.DatabaseManager;
// import entities.*;
// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.*;
// import org.junit.jupiter.api.Test;

// import java.time.LocalTime;

// class Tests
// {

//     @BeforeAll
//     static void setup()
//     {
//         DatabaseManager.getConnection("src/test/resources/credentials.txt");
//     }

//     @Test
//     void aerialCalcTest()
//     {
//         // test for aerial calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
//         var calc = new AerialCalculator();
//         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
//         Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
//         LocalTime departureTime = LocalTime.parse("00:00:00");
//         LocalTime arrivalTime = LocalTime.parse("00:00:00");
//         var route = new RouteCalculationRequest(departure, arrival, departureTime, arrivalTime, TransportType.FOOT);
//         var resultTrip = calc.calculateRoute(route);
//         assertNotNull(resultTrip.path());
//     }

//     @Test
//     void pathCalcTest()
//     {
//         // test for actual path calculation from different coords 50.85523285, 5.692237193 to 50.84027704, 5.68972678
//         var calc = new PathCalculator("src/main/resources/graph");
//         Coordinate departure = new Coordinate(50.85523285, 5.692237193);
//         Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
//         LocalTime departureTime = LocalTime.parse("00:00:00");
//         LocalTime arrivalTime = LocalTime.parse("00:00:00");
//         var route = new RouteCalculationRequest(departure, arrival, departureTime, arrivalTime, TransportType.FOOT);
//         var resultTrip = calc.calculateRoute(route);
//         assertNotNull(resultTrip.path());
//     }

<<<<<<< HEAD
//     @Test
//     void busPathTest1()
//     {
//         //test for actual path between two stops
//         var transitCalculator = new TransitCalculator();
//         var trip = transitCalculator.calculateRoute(2578413, 2578366);
//         assertNotNull(trip);
//         LocalTime expectedArrivalTime = LocalTime.parse("08:32:00");
//         assertEquals(expectedArrivalTime, trip.getArrivalTime());
//         assertNotNull(trip.path());
//     }

//     @Test
//     void busPathTest2()
//     {
//         //test for path between same stops
//         var transitCalculator = new TransitCalculator();
//         var trip = transitCalculator.calculateRoute(2578413, 2578413);
//         assertNull(trip);
=======
    @Test
    void busPathTest1()
    {
        //test for actual path between two stops
        var transitCalculator = new TransferTransitCalculator();
        var trip = transitCalculator.calculateRoute(2578413, 2578366);
        assertNotNull(trip);
        LocalTime expectedArrivalTime = LocalTime.parse("08:32:00");
        assertEquals(expectedArrivalTime, trip.getArrivalTime());
        assertNotNull(trip.path());
    }

    @Test
    void busPathTest2()
    {
        //test for path between same stops
        var transitCalculator = new TransferTransitCalculator();
        var trip = transitCalculator.calculateRoute(2578413, 2578413);
        assertNull(trip);
>>>>>>> 1dc8ed38b0b6ee5fadd5548ab553d05b6cd03145

//     }

<<<<<<< HEAD
//     @Test
//     void busPathTest3()
//     {
//         //test for path between non-existing stops
//         var transitCalculator = new TransitCalculator();
//         var trip = transitCalculator.calculateRoute(999999, 999999);
//         assertNull(trip);
//     }

//     @Test
//     void busPathTest4()
//     {
//         //test for path between existing and not existing stop
//         var transitCalculator = new TransitCalculator();
//         var trip = transitCalculator.calculateRoute(2578413, 999999);
//         assertNull(trip);
//     }
// }
=======
    @Test
    void busPathTest3()
    {
        //test for path between non-existing stops
        var transitCalculator = new TransferTransitCalculator();
        var trip = transitCalculator.calculateRoute(999999, 999999);
        assertNull(trip);
    }

    @Test
    void busPathTest4()
    {
        //test for path between existing and not existing stop
        var transitCalculator = new TransferTransitCalculator();
        var trip = transitCalculator.calculateRoute(2578413, 999999);
        assertNull(trip);
    }
}
>>>>>>> 1dc8ed38b0b6ee5fadd5548ab553d05b6cd03145
