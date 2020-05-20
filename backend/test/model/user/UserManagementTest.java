package model.user;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class UserManagementTest {

    @Test
    public void testCreateANewUser() {
        UserManagement tester = new UserManagement();


        assertTrue(tester.createUser("Herbert", "Herbert@cool.de", "Mustermann"));
        assertTrue(tester.createUser("Gisela", "Gisela@nice.com", "Neumann"));
        assertEquals("Neumann", tester.users.get(1).get(2));
        assertEquals("Gisela@nice.com", tester.users.get(1).get(1));
        assertNotEquals("Herbert", tester.users.get(1).get(0));
    }


    @Test
    public void testDeleteAUser(){
        UserManagement tester = new UserManagement();

        assertTrue(tester.createUser("Herbert", "Herbert@cool.de", "Mustermann"));
        assertTrue(tester.createUser("Ron", "Ron@cool.de", "Rot"));
        assertTrue(tester.createUser("Ute", "Ute@cool.de", "Blau"));
        assertTrue(tester.createUser("Liz", "Liz@cool.de", "Neu"));
        assertTrue(tester.deleteUser(1));
        assertFalse(tester.deleteUser(6));
    }


    @Test
    public void testUpdateAUser() {
    UserManagement tester = new UserManagement();

    tester.createUser("Herbert", "Herbert@cool.de", "Mustermann");
    tester.createUser("Ron", "Ron@cool.de", "Rot");
    tester.createUser("Ute", "Ute@cool.de", "Blau");
    List<String> test1 = new ArrayList<>();
    test1.add("Hermann");
    test1.add("Hermann@cool.de");
    test1.add("Mustermann");
    assertTrue(tester.updateUser(0, test1));
    assertFalse(tester.updateUser(5, test1));
    }
}