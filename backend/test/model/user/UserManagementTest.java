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


        Map<String, String> adressMap = new HashMap<>();

        adressMap.put("street", "Test Straße");
        adressMap.put("streetVisible", "true");
        adressMap.put("plz", "21337");
        adressMap.put("city", "Test Stadt");
        adressMap.put("country", "Test Land");

        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> answer =
                userManagement.createUser(new Ennead<>("test@test.de",
                        "test-password",
                        new Triplet<>("Nik", "Stein", true),
                        0, // rate
                        null, // picture
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        adressMap));

    }


    @Test
    public void testLoginUserSuccessfully()
    {
        Optional<String> answer = userManagement.loginUser("test@test.de", "test-password");
        Assert.assertTrue(answer.isPresent());
    }


    @Test
    public void testLoginUserFailing()
    {
        Optional<String> answer = userManagement.loginUser("Faile", "Fail");
        Assert.assertFalse(answer.isPresent());
    }
}