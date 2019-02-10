package pl.edu.agh.student.zyngier.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBTest {

    DB db;

    @Before
    public void setUp() throws Exception {
        db = new DB();
        db.openConnection();
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection();
    }

    @Test
    public void checkIfEmailAndPasswordIsCorrect_SQLInjectionTest() {
        assertEquals(false, db.checkIfEmailAndPasswordIsCorrect("test@test.com",  "or '1'='1'"));
    }

    @Test
    public void checkIfEmailAndPasswordIsCorrect_CorrectLogin() {
        assertEquals(true, db.checkIfEmailAndPasswordIsCorrect("admin@adminowo.com",  "adminadminowo"));
    }

    @Test
    public void checkIfEmailExist_ExistEmail() {
        assertEquals(false, db.checkIfEmailExist("admin@adminowo.com"));

    }
}