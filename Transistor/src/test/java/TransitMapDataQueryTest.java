import database.DatabaseManager;
import database.queries.TransitMapDataQuery;
import entities.gtfs.GDisplayRoute;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TransitMapDataQueryTest {
    @Test
    void transitMapDataQueryTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        List<GDisplayRoute> testRoutes = DatabaseManager.executeAndReadQuery(new TransitMapDataQuery());
        assertFalse(testRoutes.isEmpty());
        assertEquals(30, testRoutes.size());
    }
}
