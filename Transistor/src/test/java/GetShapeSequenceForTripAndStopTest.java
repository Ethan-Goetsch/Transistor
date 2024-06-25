import database.DatabaseManager;
import database.queries.GetTripBetweenTwoStopsQuery;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class GetShapeSequenceForTripAndStopTest {
    @Test
    void getShapeSequenceForTripAndStopTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var trip = DatabaseManager.executeAndReadQuery(new GetTripBetweenTwoStopsQuery(2578145, 2578411));
        var testSequence = DatabaseManager.executeAndReadQuery(new database.queries.GetShapeSequenceForTripAndStop(trip.id(),2578145));
        assertEquals(19,testSequence.intValue());
    }
}
