import calculators.TransitGraphCalculator;
import database.DatabaseManager;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class TransitGraphCalcTest {
    @Test
    void transitGraphCalcTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var graphCalc = new TransitGraphCalculator();
        var resultGraphPath = graphCalc.getPathDijkstra(2578129, 2578384, LocalTime.of(12, 0, 0));
        assertFalse(resultGraphPath.getEdgeList().isEmpty());
        assertEquals(9, resultGraphPath.getEdgeList().size());
        assertEquals(LocalTime.of(12,26,16), resultGraphPath.getArrivalTime());
        assertEquals(1576, resultGraphPath.getDuration());
    }
}
