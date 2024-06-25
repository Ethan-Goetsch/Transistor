import database.DatabaseManager;
import database.queries.GetAllStopsForTrip;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

public class GetAllStopsForTripTest {
    @Test
    void getAllStopsForTripTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var test = new GetAllStopsForTrip(178414978, 0, 999);
        assertNotNull(test.getStatement());
        assertNotNull(DatabaseManager.executeAndReadQuery(new GetAllStopsForTrip(1178414978, 16, 18)));
    }
}
