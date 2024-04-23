import calculators.AerialCalculator;
import calculators.PathCalculator;
import org.jetbrains.annotations.TestOnly;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests {

    @BeforeAll
    private void setUp()
    {

    }

    @BeforeEach
    //!logic before each test
    
    @Test
    void aerialCalcTest1(){

        var calculator = new AerialCalculator();
        //assertEquals();
    }

    @Test
    void pathCalcTest1(){

        var calculator = new PathCalculator();

    }

    

}