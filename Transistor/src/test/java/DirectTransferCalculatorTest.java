import calculators.DirectTransitCalculator;
import database.DatabaseManager;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotNull;

public class DirectTransferCalculatorTest {
    @Test
    public void directTransferTest() {
        DirectTransitCalculator calc = new DirectTransitCalculator();
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        List transitList = calc.calculateRoute(2578145, 2578411);
        assertNotNull(transitList);
    }
}
