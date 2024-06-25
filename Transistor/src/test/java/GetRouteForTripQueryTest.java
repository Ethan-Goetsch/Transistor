import database.DatabaseManager;
import database.queries.GetRouteForTripQuery;
import database.queries.GetTripBetweenTwoStopsQuery;
import entities.transit.TransitRoute;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GetRouteForTripQueryTest {
    @Test
    void getRouteForTripQueryTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578145, 2578411));
        TransitRoute route = DatabaseManager.executeAndReadQuery(new GetRouteForTripQuery(trip.id()));
        assertNotNull(route);
        Color color = new Color(0, 0, 255);
        assertEquals(color, route.colour());
        assertEquals(90789, route.id());
        assertEquals("5", route.name());


    }
}
