package model.user;

import org.junit.Test;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;


public class UserTest {

    @Test
    public void testCreateANewUser() {
        User tester = new User();


        assertTrue(tester.createUser("Herbert", "Herbert@cool.de", "Mustermann"));
        assertTrue(tester.createUser("Gisela", "Gisela@nice.com", "Neumann"));
        assertEquals("Neumann", tester.users.get(1).get(2));
        assertEquals("Gisela@nice.com", tester.users.get(1).get(1));
        assertNotEquals("Herbert", tester.users.get(1).get(0));
    }


    @Test
    public void testDeleteAUserUser(){
        User tester = new User();

        assertTrue(tester.createUser("Herbert", "Herbert@cool.de", "Mustermann"));
        assertTrue(tester.createUser("Ron", "Ron@cool.de", "Rot"));
        assertTrue(tester.createUser("Ute", "Ute@cool.de", "Blau"));
        assertTrue(tester.createUser("Liz", "Liz@cool.de", "Neu"));
        assertTrue(tester.deleteUser(1));
        assertFalse(tester.deleteUser(6));
    }
}