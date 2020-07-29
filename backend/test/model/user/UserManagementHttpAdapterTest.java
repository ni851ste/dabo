package model.user;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static play.inject.Bindings.bind;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;


public class UserManagementHttpAdapterTest extends WithApplication
{


    @Before
    public void setup() throws IOException
    {
        Files.createDirectories(Paths.get("test/target"));
    }

    @Override
    public Application provideApplication()
    {

        List<Integer> lendList = new ArrayList<>();
        lendList.add(2);
        lendList.add(3);
        List<Integer> borrowList = new ArrayList<>();
        borrowList.add(4);
        borrowList.add(5);
        List<Integer> pinnList = new ArrayList<>();
        pinnList.add(6);
        pinnList.add(7);
        Map<String, String> addrList = new HashMap<>();
        addrList.put("street", "Neuestrasse");
        addrList.put("streetVisible", "true");
        addrList.put("plz", "78464");
        addrList.put("city", "Konstanz");
        addrList.put("country", "Germany");


        Decade mockUser1 = new Decade<>("test-id1", "email@test.de", "hallo1", new Triplet<>("dieter", "Durst", true), 4, "no Picture", lendList, borrowList, pinnList, addrList);
        Decade mockUser2 = new Decade<>("test-id2", "email@test.de", "hallo1", new Triplet<>("dieter", "Durst", true), 0, "no Picture", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), addrList);


        Ennead mockCreateUser = new Ennead<>("email@test.de", "hallo1", new Triplet<>("dieter", "Durst", true), 0, "no Picture", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), addrList);
//        Decade mockCreateFinishedUser = new Decade(0, "email@test.de", "hallo1", new Triplet<>("dieter", "Durst", true), 0, "no Picture", new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), addrList);

        Ennead mockToBeUpdateUser = new Ennead<>("email1@test.de", "hallo1", new Triplet<>("Peter", "Fleisch", true), 3, "no Picture", lendList, borrowList, pinnList, addrList);
        Decade mockUpdateUser = new Decade<>("test-id1", "email@test.de", "hallo1", new Triplet<>("Peter", "Fleisch", true), 3, "no Picture", lendList, borrowList, pinnList, addrList);

//        Decade mockUserWithId1 = new Decade<>(1, "droelf@test.de", "hahaha34", new Triplet<>("Marie", "Jana", true), 4, "no Picture", lendList, borrowList, pinnList, addrList);
//        Decade mockUserWithId2 = new Decade<>(2, "nummer@zwei.de", "zweiundvirzig", new Triplet<>("Axel", "Schweis", true), 4, "no Picture", lendList, borrowList, pinnList, addrList);

        UserManagement userManagement = Mockito.mock(UserManagement.class);
        Mockito.when(userManagement.createUser(mockCreateUser)).thenReturn(Optional.of(mockUser2));
        Mockito.when(userManagement.getUserByID("test-id1")).thenReturn(Optional.of(mockUser1));
        Mockito.when(userManagement.deleteUser("test-id1")).thenReturn(Optional.of(mockUser1));
        Mockito.when(userManagement.updateUser("test-id2", mockToBeUpdateUser)).thenReturn(Optional.of(mockUpdateUser));

        return new GuiceApplicationBuilder()
                .in(new File("."))
                .in(Mode.TEST)
                .configure("modle.user", true)
                .bindings(bind(UserManagement.class).toInstance(userManagement))
                .build();

    }


    @Test
    public void testCreateANewUser() throws IOException
    {

        ObjectMapper mapper = new ObjectMapper();
        File createUserFile = new File("test/resources/createUser.json");
        JsonNode createUserJson = mapper.readTree(createUserFile);

        Http.RequestBuilder requestshow = new Http.RequestBuilder()
                .method(POST)
                .uri("/user/create")
                .bodyJson(createUserJson);

        Result result = route(app, requestshow);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test/target/createUserTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File actuallyFile = new File("test/resources/newUser.json");

        JsonNode testJson = mapper.readTree(testFile);
        JsonNode actuallyJson = mapper.readTree(actuallyFile);
        assertEquals(testJson, actuallyJson);


    }


    @Test
    public void testDeleteAUser() throws IOException
    {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/user/delete/test-id1");

        Result result = route(app, request);

        assertEquals(OK, result.status());
    }


    @Test
    public void testUpdateAUser() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        File updateUserFile = new File("test/resources/updateUser.json");
        JsonNode updateUserJson = mapper.readTree(updateUserFile);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/user/update/test-id2")
                .bodyJson(updateUserJson);

        Result result = route(app, request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test/target/updateUserTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File updateFile = new File("test/resources/createUpdatedUser.json");
        JsonNode testJson = mapper.readTree(resultJsonToString);
        JsonNode actuallyJson = mapper.readTree(updateFile);

        assertEquals(actuallyJson, testJson);
    }


    @Test
    public void getUserById() throws IOException
    {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/user/find/test-id1");

        Result result = route(app, request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test/target/getUserTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File actuallyFile = new File("test/resources/getUser.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode testJson = mapper.readTree(resultJsonToString);
        JsonNode actuallyJson = mapper.readTree(actuallyFile);

        assertEquals(actuallyJson, testJson);

    }
}