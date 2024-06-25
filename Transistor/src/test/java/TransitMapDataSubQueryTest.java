import database.DatabaseManager;
import entities.gtfs.GDisplayRoute;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TransitMapDataSubQueryTest {
    @Test
    void transitMapDataSubQueryTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        List<GDisplayRoute> testRoutes = DatabaseManager.executeAndReadQuery(new database.queries.TransitMapDataSubQuery());
        assertFalse(testRoutes.isEmpty());
        assertEquals(34,testRoutes.size());

    }
}
