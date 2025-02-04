package simba.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimbaTest {

    @Test
    public void parserTest1(){
        Parser parser1 = new Parser("delete 1");
        assertEquals(1, parser1.idxToDelete());
    }

    @Test
    public void parserTest2(){
        Parser parser2 = new Parser("delete 2");
        assertEquals(2, parser2.idxToDelete());
    }
}
