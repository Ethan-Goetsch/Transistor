import database.queries.GetClosetStops;
import entities.transit.TransitStop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import database.DatabaseManager;
import entities.Coordinate;
import java.util.List;

public class GetClosetStopsTest {
    @Test
    void getClosetStopsTest(){
        var query = new GetClosetStops(new Coordinate(40.748817, -73.985428), 5);
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var stopCord = new Coordinate(50.8503582678571,5.69144829642857);
        var nearStops = DatabaseManager.executeAndReadQuery(new GetClosetStops(stopCord, 5));
        assertEquals(5, nearStops.size());
    }
}