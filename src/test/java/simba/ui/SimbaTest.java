package simba.ui;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class SimbaTest {
    Parser parser1 = new Parser("delete 1");
    Parser parser2 = new Parser("delete 2");

    @Test
    public void dummyTest(){
        assertEquals(1, parser1.idxToDelete());
    }

    @Test
    public void anotherDummyTest(){
        assertEquals(2, parser2.idxToDelete());
    }
}
