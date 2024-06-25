import database.DatabaseManager;
import database.queries.GetAllTripsMaasSubQuery;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class GetAllTripsMaasSubQueryTest {
    @Test
    void getAllTripsMaasSubQueryTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var map = DatabaseManager.executeAndReadQuery(new GetAllTripsMaasSubQuery());
        assertNotNull(map);

    }
}
