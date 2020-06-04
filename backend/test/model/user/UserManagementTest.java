package model.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class UserManagementTest {

    @Test
    public void testCreateANewUser() {
        UserManagement tester = new UserManagement();

        assertTrue(tester.createUser("Herby", "Herbert@cool.de", "Herbert Mustermann", 34));
        assertTrue(tester.createUser("Gise", "Gisela@nice.com", "Gisela Neumann", 39));
        assertEquals("Gisela Neumann", tester.users.get(1).get(2));
        assertEquals("Gisela@nice.com", tester.users.get(1).get(1));
        assertNotEquals("Herby", tester.users.get(1).get(0));
        assertEquals("Herby", tester.users.get(0).get(0));
        assertEquals("39", tester.users.get(1).get(3));
        assertEquals("34", tester.users.get(0).get(3));

    }


    @Test
    public void testDeleteAUser(){
        UserManagement tester = new UserManagement();

        assertTrue(tester.createUser("Herby", "Herbert@cool.de", "Herbert Mustermann" ,34));
        assertTrue(tester.createUser("Ron", "Ron@cool.de", "Ronald Rot", 45));
        assertTrue(tester.createUser("Uzzy", "Ute@cool.de", "Ute Blau", 23));
        assertTrue(tester.createUser("Lisele", "Liz@cool.de", "Liz Neu", 45));
        assertTrue(tester.deleteUser(1).contains("User: Ron is deleted"));
        assertTrue(tester.deleteUser(6).contains("User is not known"));
    }


    @Test
    public void testUpdateAUser() throws JsonProcessingException {
    UserManagement tester = new UserManagement();

    tester.createUser("Herby", "Herbert@cool.de", "Herbert Mustermann", 34);
    tester.createUser("Ron", "Ron@cool.de", "Ronald Rot", 74);
    tester.createUser("Ute", "Ute@cool.de", "Ute Blau",19);

    String json = "{\"id\":\"0\",\"name\":\"John Doe\",\"username\":\"Herby\",\"email\":\"Herbert@cool.de\",\"age\":\"34\"}";
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(json);

    String json1 = "{\"id\":\"1\",\"name\":\"Ronald Rot\",\"username\":\"Ron\",\"email\":\"Ron@cool.de\",\"age\":\"34\"}";
    ObjectMapper mapper1 = new ObjectMapper();
    JsonNode node1 = mapper1.readTree(json1);

    System.out.println("New Data:\n\tUsername:\t\"Herby\"\n\tAge:\t\t\"34\"\n\tEmail:\t\t\"Herbert@cool.de\"\n\tName:\t\t\"John Doe\"\n\tUserId:\t\t0");

    assertTrue(tester.updateUser(node).contains("New Data:\n\tUsername:\t\"Herby\"\n\tAge:\t\t\"34\"\n\tEmail:\t\t\"Herbert@cool.de\"\n\tName:\t\t\"John Doe\"\n\tUserId:\t\t0"));
    assertFalse(tester.updateUser(node1).contains("New Data:\n\tUsername:\tHerby\n\tAge:\t\t34\n\tEmail:\t\tHerbert@cool.de\n\tName:\t\tHerbert Mustermann\n\tUserId:\t\t0"));
    }


    @Test
    public void testFindeUser() throws JsonProcessingException {
        UserManagement tester = new UserManagement();

        tester.createUser("Herby", "Herbert@cool.de", "Herbert Mustermann", 34);
        tester.createUser("Ron", "Ron@cool.de", "Ronald Rot", 74);
        tester.createUser("Ute", "Ute@cool.de", "Ute Blau",19);

        //"{\"id\":" + i + ",\"username\":\"" + search.get(0) + "\",\"email:\"" + search.get(1) + "\",\"name:\"" + search.get(2) + "\",\"age:\"" + search.get(3) + "\"}"

        String json1 = "{\"id\":1,\"username\":\"Ron\",\"email\":\"Ron@cool.de\",\"name\":\"Ronald Rot\",\"age\":\"74\"}";
        ObjectMapper mapper1 = new ObjectMapper();
        JsonNode node1 = mapper1.readTree(json1);

        String json2 = "{\"message\":\"User not found\"}";
        ObjectMapper mapper2 = new ObjectMapper();
        JsonNode node2 = mapper2.readTree(json2);

        //System.out.println(node2);
        //System.out.println(node1);
        assertEquals(tester.findeUser("Ron"), node1);
        assertEquals(tester.findeUser("Harry"), node2);

    }
}