package model.user;

import java.util.*;


public class User {
    Map<Integer, List> users = new HashMap<>();
    int id = 0;

    public void createUser(String name, String email, String lastName){
        List<String> values = new ArrayList<>();
        values.add(name);
        values.add(email);
        values.add(lastName);
        users.put(this.id, values);
        this.id += 1;
        System.out.println("Person: " + values.get(2) +
                "\n\twas created with the Username: " + values.get(0) +
                "\n\twith the id: " + this.id);
    }

    public void deleteUser(int index){
        List<String> values = new ArrayList<>();
        values.add("User is removed");
        users.remove(index);
        users.put(index, values);
    }

}
