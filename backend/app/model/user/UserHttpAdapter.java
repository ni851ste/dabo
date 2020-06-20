package model.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class UserHttpAdapter {

    UserManagement adapter = new UserManagement();

    public UserHttpAdapter() {
    }
    UserManagement userManagement = new UserManagement();


    public Result createUser(Request create)
    {

        JsonNode json = create.body().asJson();


        List<Integer> lendList = new ArrayList<>();
        json.get("toLend").forEach(node -> lendList.add(node.asInt()));

        List<Integer> borrowList = new ArrayList<>();
        json.get("borrowed").forEach(node -> borrowList.add(node.asInt()));

        List<Integer> pinnList = new ArrayList<>();
        json.get("pinned").forEach(node -> pinnList.add(node.asInt()));

        List<String> addrList = new ArrayList<>();
        json.get("address").forEach(node -> addrList.add(node.asText()));

        List<String> nameList = new ArrayList<>();
        nameList.add(json.get("firstName").asText());
        nameList.add(json.get("lastName").asText());




        Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
                toBeCreatedUser =
                new Ennead(json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        json.get("rating").asInt(),
                        json.get("picture").asText(),
                        lendList,
                        borrowList,
                        pinnList,
                        addrList);

        Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
                createdUser = userManagement.createUser(toBeCreatedUser);

        if(createdUser.getValue0() == -1)
        {
            return badRequest("Creating user failed");
        }
        else
        {

            JSONObject returnJson = new JSONObject()
                    .put("id", createdUser.getValue0())
                    .put("email", createdUser.getValue1())
                    .put("password", createdUser.getValue2())
                    .put("name", createdUser.getValue3())
                    .put("rating", createdUser.getValue4())
                    .put("picture", createdUser.getValue5())
                    .put("toLend", createdUser.getValue6())
                    .put("borrowed", createdUser.getValue7())
                    .put("pinned", createdUser.getValue8())
                    .put("address", createdUser.getValue9());
            System.out.println(returnJson.toString());
            return ok(returnJson.toString())
                    .as("application/json");
        }

    }

    public Result deleteUser(int id){
        Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
                deletedUser = userManagement.deleteUser(id);
        if(deletedUser.getValue0() == -1)
        {
            return badRequest("No User to delete");
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", deletedUser.getValue0())
                    .put("email", deletedUser.getValue1())
                    .put("password", deletedUser.getValue2())
                    .put("name", deletedUser.getValue3())
                    .put("rating", deletedUser.getValue4())
                    .put("picture", deletedUser.getValue5())
                    .put("toLend", deletedUser.getValue6())
                    .put("borrowed", deletedUser.getValue7())
                    .put("pinned", deletedUser.getValue8())
                    .put("address", deletedUser.getValue9());
            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateUser(int id, Request update) {

        JsonNode json = update.body().asJson();

        System.out.println("worked till HTTPAdappter");

        List<Integer> lendList = new ArrayList<>();
        json.get("toLend").forEach(node -> lendList.add(node.asInt()));

        List<Integer> borrowList = new ArrayList<>();
        json.get("borrowed").forEach(node -> borrowList.add(node.asInt()));

        List<Integer> pinnList = new ArrayList<>();
        json.get("pinned").forEach(node -> pinnList.add(node.asInt()));

        List<String> addrList = new ArrayList<>();
        json.get("address").forEach(node -> addrList.add(node.asText()));


        List<String> nameList = new ArrayList<>();
        nameList.add(json.get("firstName").asText());
        nameList.add(json.get("lastName").asText());

        Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
                toBeUpdatedUser =
                new Ennead(json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        json.get("rating").asInt(),
                        json.get("picture").asText(),
                        lendList,
                        borrowList,
                        pinnList,
                        addrList);

        Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
                updatedUser = userManagement.updateUser(id, toBeUpdatedUser);
        if(updatedUser.getValue0() == -1)
        {
            return badRequest("No user found");
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", updatedUser.getValue0())
                    .put("email", updatedUser.getValue1())
                    .put("password", updatedUser.getValue2())
                    .put("name", updatedUser.getValue3())
                    .put("rating", updatedUser.getValue4())
                    .put("picture", updatedUser.getValue5())
                    .put("toLend", updatedUser.getValue6())
                    .put("borrowed", updatedUser.getValue7())
                    .put("pinned", updatedUser.getValue8())
                    .put("address", updatedUser.getValue9());
            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

}

