package model.user;


import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert.*;

import java.util.*;

public class UserManagementTest
{
    UserManagement userManagement;

    @Before
    public void setup()
    {
        userManagement = new UserManagement();


        Map<String, String> addressMap = new HashMap<>();

        addressMap.put("street", "Test Straße");
        addressMap.put("streetVisible", "true");
        addressMap.put("plz", "21337");
        addressMap.put("city", "Test Stadt");
        addressMap.put("country", "Test Land");

        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> answer =
                userManagement.createUser(new Ennead<>("test@test.de",
                        "test-password",
                        new Triplet<>("Nik", "Stein", true),
                        0, // rate
                        null, // picture
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        addressMap));

    }


    @Test
    public void testLoginUserSuccessfully()
    {
        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> answer =
                userManagement.loginUser("test@test.de", "test-password");

        Assert.assertTrue(answer.isPresent());
    }


    @Test
    public void testLoginUserFailing()
    {
        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> answer =
                userManagement.loginUser("Faile", "Fail");

        Assert.assertFalse(answer.isPresent());
    }
}