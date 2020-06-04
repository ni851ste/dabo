package model.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;


public class UserManagement {
    Map<Integer, List<String>> users = new HashMap<>();
    int globalIdCounter = 0;

    public boolean createUser(String username, String email, String name, int age) {
        String ageToString = String.valueOf(age);
        int localIdCounter = this.globalIdCounter;
        List<String> values = new ArrayList<>();
        values.add(username);
        values.add(email);
        values.add(name);
        values.add(ageToString);
        users.put(this.globalIdCounter, values);
        this.globalIdCounter += 1;
        System.out.println("Username: " + username + "\nE-mail: " + email + "\nName: " + name + "\nage: " + age);
        System.out.println(users);
        return users.containsKey(localIdCounter);
    }


    public String deleteUser(int userId) {
        if (users.containsKey(userId)) {
            String name = users.get(userId).get(0);
            users.remove(userId);
            String deleted = "User: " + name + " is deleted";
            System.out.println(deleted);
            return (deleted);
        } else {
            System.out.println("User is not known");
            return ("User is not known");
        }
    }

    public String updateUser(JsonNode json) {
        String username = json.get("username").toString();
        int id = Integer.parseInt(json.get("id").textValue());
        List<String> newValue = new ArrayList<String>();
        newValue.add(username);
        newValue.add(json.get("email").toString());
        newValue.add(json.get("name").toString());
        newValue.add(json.get("age").toString());
        if (users.containsKey(id)) {
            users.replace(id, newValue);
            String answer = "New Data:\n\tUsername:\t" + username + "\n\tAge:\t\t" + users.get(id).get(3)
                    + "\n\tEmail:\t\t" + users.get(id).get(1)
                    + "\n\tName:\t\t" + users.get(id).get(2)
                    + "\n\tUserId:\t\t" + id;
            System.out.println(answer);
            return (answer);
        } else {

            return ("User not found");
        }
    }

    public JsonNode findeUser(String toFinde) throws JsonProcessingException {
        System.out.println(toFinde);
        for (int i = 0; i < globalIdCounter; i++) {
            List<String> search = users.get(i);
            if (search.contains(toFinde)) {
                String username = search.get(0);
                String email = search.get(1);
                String name = search.get(2);
                String age = search.get(3);

                String json = "{\"id\":" + i + ",\"username\":\"" + username + "\",\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"age\":\"" + age + "\"}";
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();
                // convert JSON string to `JsonNode`
                System.out.println(mapper);
                System.out.println(json);
                JsonNode node = mapper.readTree(json);
                System.out.println(node);
                return node;
            } else {
                continue;
            }
        }// JSON string
        String json = "{\"message\":\"User not found\"}";
        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();
        // convert JSON string to `JsonNode`
        JsonNode node1 = mapper.readTree(json);
        System.out.println(node1);
        return node1;
    }

}
