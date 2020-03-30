package com.example.kmsquiz;
import org.junit.Test;
import static org.junit.Assert.*;


public class QuestionTest {
    Question question = new Question("A","B","C","D",1,2);

    @Test
    public void testGetAnswer(){
        int expected = 1;
        assertEquals(question.getAnswerNr(),expected);
    }

    @Test
    public void testSetAnswer(){
        question.setAnswerNr(2);
        int expected = 2;
        assertEquals(question.getAnswerNr(),expected);
    }

    @Test
    public void testGetCategoryID(){
        int expected = 2;
        assertEquals(question.getCategoryID(), expected);
    }

    @Test
    public void testSetCategoryID(){
        question.setCategoryID(3);
        int expected = 3;
        assertEquals(question.getCategoryID(), expected);
    }

    @Test
    public void testGetQuestion(){
        String expected = "A";
        assertEquals(question.getQuestion(),expected);
    }

    @Test
    public void testSetQuestion() {
        question.setQuestion("X");
        String expected = "X";
        assertEquals(question.getQuestion(),expected);
    }

    @Test
    public void testSetGetOption(){
        question.setOption1("Y");
        question.setOption2("Z");
        question.setOption3("W");
        String expected1 = "Y";
        String expected2 = "Z";
        String expected3 = "W";
        assertEquals(question.getOption1(),expected1);
        assertEquals(question.getOption2(),expected2);
        assertEquals(question.getOption3(),expected3);
    }




    // Not sure how to do the methods with Parcel

}
