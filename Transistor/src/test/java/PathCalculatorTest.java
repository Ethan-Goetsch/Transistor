import calculators.*;
import entities.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

public class PathCalculatorTest {
    @Test
    void pathCalcTest(){
        var calc = new PathCalculator("src/test/resources/graph");
        Coordinate departure = new Coordinate(50.85523285, 5.692237193);
        Coordinate arrival = new Coordinate(50.84027704, 5.68972678);
        LocalTime departureTime = LocalTime.parse("00:00:00");
        var route = new RouteCalculationRequest(departure, arrival, departureTime, TransportType.FOOT);
        var resultTrip = calc.calculateRoute(route);
        LocalTime arrivalTime = resultTrip.getArrivalTime();
        assertEquals("00:28:33", arrivalTime.toString());
    }
}
