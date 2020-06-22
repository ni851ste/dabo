package model.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class UserHttpAdapter
{

    UserManagement userManagement = new UserManagement();


    public Result createUser(Request create)
    {

        JsonNode json = create.body().asJson();


        List<Integer> lendList = new ArrayList<>();

        List<Integer> borrowList = new ArrayList<>();

        List<Integer> pinnList = new ArrayList<>();

        List<String> addrList = new ArrayList<>();
        json.get("address").forEach(node -> addrList.add(node.asText()));

        Triplet<String, String, Boolean> nameList = new Triplet<>(json.get("firstName").asText(),
                json.get("lastName").asText(),
                json.get("lastNameVisible").asBoolean());


        Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>>> createdUser =
                userManagement.createUser(new Ennead(json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        json.get("rating").asInt(),
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
                    .put("rating", createdUser.get().getValue4())
                    .put("picture", createdUser.get().getValue5())
                    .put("toLend", createdUser.get().getValue6())
                    .put("borrowed", createdUser.get().getValue7())
                    .put("pinned", createdUser.get().getValue8())
                    .put("address", createdUser.get().getValue9());


            return ok(returnJson.toString())
                    .as("application/json");
        }

    }

    public Result getUserById(int id)
    {

        Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>>>
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
                    .put("rating", foundUser.get().getValue4())
                    .put("picture", foundUser.get().getValue5())
                    .put("toLend", foundUser.get().getValue6())
                    .put("borrowed", foundUser.get().getValue7())
                    .put("pinned", foundUser.get().getValue8())
                    .put("address", foundUser.get().getValue9());

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result deleteUser(int id){
        Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>>>
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
                    .put("rating", deletedUser.get().getValue4())
                    .put("picture", deletedUser.get().getValue5())
                    .put("toLend", deletedUser.get().getValue6())
                    .put("borrowed", deletedUser.get().getValue7())
                    .put("pinned", deletedUser.get().getValue8())
                    .put("address", deletedUser.get().getValue9());

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateUser(int id, Request update)
    {

        JsonNode json = update.body().asJson();

        List<Integer> lendList = new ArrayList<>();
        json.get("toLend").forEach(node -> lendList.add(node.asInt()));

        List<Integer> borrowList = new ArrayList<>();
        json.get("borrowed").forEach(node -> borrowList.add(node.asInt()));

        List<Integer> pinnList = new ArrayList<>();
        json.get("pinned").forEach(node -> pinnList.add(node.asInt()));

        List<String> addrList = new ArrayList<>();
        json.get("address").forEach(node -> addrList.add(node.asText()));


        Triplet<String, String, Boolean> nameList = new Triplet<>(json.get("firstName").asText(),
                json.get("lastName").asText(),
                json.get("lastNameVisible").asBoolean());

        Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>> toBeUpdatedUser =
                new Ennead(json.get("email").asText(),
                        json.get("password").asText(),
                        nameList,
                        json.get("rating").asInt(),
                        json.get("picture").asText(),
                        lendList,
                        borrowList,
                        pinnList,
                        addrList);

        Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>>>
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
                    .put("rating", updatedUser.get().getValue4())
                    .put("picture", updatedUser.get().getValue5())
                    .put("toLend", updatedUser.get().getValue6())
                    .put("borrowed", updatedUser.get().getValue7())
                    .put("pinned", updatedUser.get().getValue8())
                    .put("address", updatedUser.get().getValue9());
            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

}

