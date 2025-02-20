package simba.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimbaTest {

    @Test
    public void parserTest1() {
        Parser parser1 = new Parser("delete 1");
        assertEquals(1, parser1.idxToUse());
    }

    @Test
    public void parserTest2() {
        Parser parser2 = new Parser("delete 2");
        assertEquals(2, parser2.idxToUse());
    }
}
