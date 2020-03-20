package com.example.kmsquiz;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryTest {
    Category cat = new Category();


    @Test
    public void testGetId() {
        cat.setId(1);
        int expected1 = 1;
        assertEquals(cat.getId(), expected1);
    }

    @Test
    public void testGetName() {
        cat.setName("Drug");
        String expected = "Drug";
        assertEquals(cat.getName(), expected);
    }


    @Test
    public void testToString() {
        cat.setName("Drug");
        String expected = "Drug";
       assertEquals(cat.toString(),expected);
    }

}
