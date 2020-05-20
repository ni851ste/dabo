package model.user;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.*;
import javax.inject.Inject;



public class UserManagement {
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

    public boolean updateUser(int id, List<String> newValues) {
        if (users.containsKey(id)) {
            users.replace(id, newValues);
            return (users.get(id) == newValues);
        }
        System.out.println("User not in System found");
        return false;
    }

}
