package model.user;

import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;
import javax.inject.Inject;


public class UserManagement {
    Map<Integer, List<String>> users = new HashMap<>();
    int globalIdCounter = 0;

    public boolean createUser(String username, String email, String name) {
        int localIdCounter = this.globalIdCounter;
        List<String> values = new ArrayList<>();
        values.add(username);
        values.add(email);
        values.add(name);
        users.put(this.globalIdCounter, values);
        this.globalIdCounter += 1;
        System.out.println("Username: " + username + "\nE-mail: " + email + "\nName: " + name);
        return users.containsKey(localIdCounter);
    }


    public boolean deleteUser(int userId) {
        if (users.containsKey(userId)) {
            String name = users.get(userId).get(0);
            users.remove(userId);
            System.out.println("User: " + name + " is deleted");
            return true;
        } else {
            System.out.println("User is not known");
            return false;
        }
    }

    public boolean updateUser(int id, List<String> newValues) {
        if (users.containsKey(id)) {
            users.replace(id, newValues);
            System.out.println("User: " + users.get(id).get(0) + " is new username");
            return (users.get(id) == newValues);
        }
        System.out.println("User not in System found");
        return false;
    }

}
