package com.example.kmsquiz;
import com.example.kmsquiz.data.model.LoggedInUser;

import org.junit.Test;
import static org.junit.Assert.*;


public class LoggedInUserTest {
    LoggedInUser user = new LoggedInUser("10","User");

    @Test
    public void testGetUserID() {
        String expected = "10";
        assertEquals(user.getUserId(),expected);
    }

    @Test
    public void testGetDisplayName() {
        String expected = "User";
        assertEquals(user.getDisplayName(),expected);
    }



}
