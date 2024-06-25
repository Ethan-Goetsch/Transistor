import calculators.TransferTransitCalculator;
import calculators.TransitGraphCalculator;
import database.DatabaseManager;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TransferTransitCalcTest {
    @Test
    void transferTransitCalcTest(){
        var connection = DatabaseManager.getConnection("src/test/resources/credentials.txt");
        var transitGraphCalc = new TransitGraphCalculator();
        var transferCalc = new TransferTransitCalculator(transitGraphCalc);
        var result = transferCalc.calculateRoute(2578145, 2578411);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
    }
}
