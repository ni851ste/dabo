package model.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.*;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class UserHttpAdapter
{
    @Inject
    UserManagement userManagement = new UserManagement();


    public Result createUser(Request create) {

        JsonNode json = create.body().asJson();


        List<Integer> lendList = new ArrayList<>();

        List<Integer> borrowList = new ArrayList<>();

        List<Integer> pinnList = new ArrayList<>();

        Map<String, String> addrList = new HashMap<>();
        addrList.put("street", json.get("street").asText());
        addrList.put("streetVisible", json.get("streetVisible").asText());
        addrList.put("plz", json.get("plz").asText());
        addrList.put("city", json.get("city").asText());
        addrList.put("country", json.get("country").asText());

        Triplet<String, String, Boolean> nameList = new Triplet<>(json.get("firstName").asText(),
                json.get("lastName").asText(),
                json.get("lastNameVisible").asBoolean());

        Integer rateing = 0;

        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> createdUser =
                userManagement.createUser(new Ennead(
                        json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        rateing,
                        json.get("picture").asText(),
                        lendList,
                        borrowList,
                        pinnList,
                        addrList));


        if(createdUser.isEmpty())
        {
            return badRequest();
        }
        else
        {

            JSONObject returnJson = new JSONObject()
                    .put("id", createdUser.get().getValue0())
                    .put("email", createdUser.get().getValue1())
                    .put("password", createdUser.get().getValue2())
                    .put("firstName", createdUser.get().getValue3().getValue0())
                    .put("lastName", createdUser.get().getValue3().getValue1())
                    .put("lastNameVisible", createdUser.get().getValue3().getValue2())
                    .put("ratings", createdUser.get().getValue4())
                    .put("picture", createdUser.get().getValue5())
                    .put("insertedArticlesId", createdUser.get().getValue6())
                    .put("borrowedArticlesId", createdUser.get().getValue7())
                    .put("pinnedArticledId", createdUser.get().getValue8())
                    .put("address", createdUser.get().getValue9());

            return ok(returnJson.toString())
                    .as("application/json");
        }

    }

    public Result getUserById(String id)
    {

        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String,String>>>
                foundUser = userManagement.getUserByID(id);

        if (foundUser.isEmpty())
        {
            return badRequest();
        }
        else
        {

            JSONObject returnJson = new JSONObject()
                    .put("id", foundUser.get().getValue0())
                    .put("email", foundUser.get().getValue1())
                    .put("password", foundUser.get().getValue2())
                    .put("firstName", foundUser.get().getValue3().getValue0())
                    .put("lastName", foundUser.get().getValue3().getValue1())
                    .put("lastNameVisible", foundUser.get().getValue3().getValue2())
                    .put("ratings", foundUser.get().getValue4())
                    .put("picture", foundUser.get().getValue5())
                    .put("insertedArticlesId", foundUser.get().getValue6())
                    .put("borrowedArticlesId", foundUser.get().getValue7())
                    .put("pinnedArticledId", foundUser.get().getValue8())
                    .put("address", foundUser.get().getValue9());


            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result deleteUser(String id){
        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String,String>>>
                deletedUser = userManagement.deleteUser(id);

        if(deletedUser.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", deletedUser.get().getValue0())
                    .put("email", deletedUser.get().getValue1())
                    .put("password", deletedUser.get().getValue2())
                    .put("firstName", deletedUser.get().getValue3().getValue0())
                    .put("lastName", deletedUser.get().getValue3().getValue1())
                    .put("lastNameVisible", deletedUser.get().getValue3().getValue2())
                    .put("ratings", deletedUser.get().getValue4())
                    .put("picture", deletedUser.get().getValue5())
                    .put("insertedArticlesId", deletedUser.get().getValue6())
                    .put("borrowedArticlesId", deletedUser.get().getValue7())
                    .put("pinnedArticledId", deletedUser.get().getValue8())
                    .put("address", deletedUser.get().getValue9());

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateUser(String id, Request update)
    {

        JsonNode json = update.body().asJson();

        List<Integer> lendList = new ArrayList<>();
        json.get("insertedArticlesId").forEach(node -> lendList.add(node.asInt()));

        List<Integer> borrowList = new ArrayList<>();
        json.get("borrowedArticlesId").forEach(node -> borrowList.add(node.asInt()));

        List<Integer> pinnList = new ArrayList<>();
        json.get("pinnedArticledId").forEach(node -> pinnList.add(node.asInt()));

        Map<String, String> addrList = new HashMap<>();
        addrList.put("street", json.get("address").get("street").asText());
        addrList.put("streetVisible", json.get("address").get("streetVisible").asText());
        addrList.put("plz", json.get("address").get("plz").asText());
        addrList.put("city", json.get("address").get("city").asText());
        addrList.put("country", json.get("address").get("country").asText());


        Triplet<String, String, Boolean> nameList = new Triplet<>(
                json.get("firstName").asText(),
                json.get("lastName").asText(),
                json.get("lastNameVisible").asBoolean());

        Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> toBeUpdatedUser =
                new Ennead(json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        json.get("ratings").asInt(),
                        json.get("picture").asText(),
                        lendList,
                        borrowList,
                        pinnList,
                        addrList);

        Optional<Decade<String, String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String,String>>>
                updatedUser = userManagement.updateUser(id, toBeUpdatedUser);
        
        if(updatedUser.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", updatedUser.get().getValue0())
                    .put("email", updatedUser.get().getValue1())
                    .put("password", updatedUser.get().getValue2())
                    .put("firstName", updatedUser.get().getValue3().getValue0())
                    .put("lastName", updatedUser.get().getValue3().getValue1())
                    .put("lastNameVisible", updatedUser.get().getValue3().getValue2())
                    .put("ratings", updatedUser.get().getValue4())
                    .put("picture", updatedUser.get().getValue5())
                    .put("insertedArticlesId", updatedUser.get().getValue6())
                    .put("borrowedArticlesId", updatedUser.get().getValue7())
                    .put("pinnedArticledId", updatedUser.get().getValue8())
                    .put("address", updatedUser.get().getValue9());

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result loginUser(Request request)
    {
        JsonNode json = request.body().asJson();

        String userEmail = json.get("email").asText();
        String userPassword = json.get("password").asText();

        Optional<String> userId =   userManagement.loginUser(userEmail, userPassword);

        if (userId.isPresent()) {
            JSONObject returnJson = new JSONObject()
                    .put("userHash", userId.get());

            return ok(returnJson.toString())
                    .as("application/json");
        }
        return badRequest("No user found with given credentials");

    }

}

