package model.user;

import org.junit.Test;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;


public class UserTest {

    @Test
    public void testCreateANewUser() {
        User tester = new User();

        tester.createUser("Herbert", "Herbert@cool.de", "Mustermann");
        assertEquals("Herbert", tester.users.get(0).get(0));
        assertEquals("Mustermann", tester.users.get(0).get(2));
        tester.createUser("Gisela", "Gisela@nice.com", "Neumann");
        assertEquals("Gisela", tester.users.get(1).get(0));
        assertEquals("Gisela@nice.com", tester.users.get(1).get(1));
        assertNotEquals("Herbert", tester.users.get(1).get(0));
    }
    @Test
    public void testDeleteAUserUser(){
        User tester = new User();

        tester.createUser("Herbert", "Herbert@cool.de", "Mustermann");
        tester.createUser("Ron", "Ron@cool.de", "Rot");
        tester.createUser("Ute", "Ute@cool.de", "Blau");
        tester.createUser("Liz", "Liz@cool.de", "Neu");
        assertEquals("Herbert", tester.users.get(0).get(0));
        assertEquals("Mustermann", tester.users.get(0).get(2));
        assertEquals("Ron", tester.users.get(1).get(0));
        assertEquals("Rot", tester.users.get(1).get(2));
        assertEquals("Ute", tester.users.get(2).get(0));
        assertEquals("Blau", tester.users.get(2).get(2));
        assertEquals("Liz", tester.users.get(3).get(0));
        assertEquals("Neu", tester.users.get(3).get(2));
        tester.deleteUser(1);
        //assertNull(tester.users.get(1).get(0));
        //assertNull(tester.users.get(1).get(2));
        assertEquals("User is removed", tester.users.get(1).get(0));

    }
}