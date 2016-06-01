import helperClasses.DataChecking;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elizaveta on 26.05.2016.
 */
public class DataCheckingTest {
    @Test
    public void StringCheckingTest() {
        assertEquals("Иван",DataChecking.checkOneWord("Иван"), true);
        assertEquals("lalal1@",DataChecking.checkOneWord("lalal1@"), true);
        assertEquals("lll l",DataChecking.checkOneWord("lll l"), false);
        assertEquals("lll",DataChecking.checkQA("lll"), true);
        assertEquals("fkfkf---fg",DataChecking.checkQA("fkfkf---fg"), false);


    }
}
