import database.DatabaseManager;
import entities.gtfs.GDisplayRoute;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TransitMapDataSubQueryTest {
    @Test
    void transitMapDataSubQueryTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        List<GDisplayRoute> testRoutes = DatabaseManager.executeAndReadQuery(new database.queries.TransitMapDataSubQuery());
        assertFalse(testRoutes.isEmpty());
        assertTrue(testRoutes.size()==34);

    }
}
