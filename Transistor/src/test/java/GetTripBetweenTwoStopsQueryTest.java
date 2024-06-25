import database.DatabaseManager;
import database.queries.GetTripBetweenTwoStopsQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GetTripBetweenTwoStopsQueryTest {
    @BeforeAll
    static void setup(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
    }
    @Test
    void getTripBetweenTwoStopsQueryTest() {
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578145, 2578411));
        assertEquals(178414848, trip.id());
    }

    @Test
    void getNonExistingTrip(){
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578145, 999));
        assertNull(trip);
    }

    @Test
    void getImpossibleTrip(){
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578145, 2578190));
        assertNull(trip);
    }

}
