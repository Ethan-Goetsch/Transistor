import database.DatabaseManager;
import database.queries.GetAllShapesBetweenStops;
import entities.transit.shapes.TransitShape;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;


public class GetAllShapesBetweenStopsTest {

        @Test
        void testGetAllShapesBetweenStops() {
            var query = new GetAllShapesBetweenStops(176959750, 1, 2);
            var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
            ResultSet rs = query.executeQuery(connection);
            List<TransitShape> shapes = query.readResult(rs);
            assertTrue(shapes.size() > 0);
        }

}
