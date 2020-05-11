package model.user;

import java.util.*;


public class User {
    Map<Integer, List<String>> users = new HashMap<>();
    int globalIdCounter = 0;

    public boolean createUser(String name, String email, String lastName) {
        int localIdCounter = this.globalIdCounter;
        List<String> values = new ArrayList<>();
        values.add(name);
        values.add(email);
        values.add(lastName);
        users.put(this.globalIdCounter, values);
        this.globalIdCounter += 1;
        return users.containsKey(localIdCounter);
    }


    public boolean deleteUser(int userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            return true;
        }
        return false;
    }

}
