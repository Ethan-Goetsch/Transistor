import database.DatabaseManager;
import database.queries.GetAllTripsMaasQuery;
import entities.Trip;
import entities.TransitGraphEntities.TTrip;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetAllTripsMaasQueryTest {
    @Test
    void getAllTripsMaasTest() {
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        List<TTrip> trips = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasQuery());
        assertTrue(trips.size()>0);
        assertEquals(5172, trips.size());
    }
}