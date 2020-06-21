package model.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.ok;

public class UserHttpAdapter {

    UserManagement adapter = new UserManagement();

    public UserHttpAdapter() {
    }

    public Result createUser(Http.Request create) {
        JsonNode json = create.body().asJson();
        adapter.createUser(json.get("username").toString(),
                json.get("email").toString(),
                json.get("name").toString(),
                Integer.parseInt(json.get("age").textValue()));

        return ok("Username: "+ json.get("username").toString() +
                "\nName: " + json.get("name").toString() +
                "\nAge: " + Integer.parseInt(json.get("age").textValue()) +
                "\nemail: " + json.get("email").toString());
    }

    public Result deleteUser(int id) {
        String answer = adapter.deleteUser(id);
        if(answer.contains("User is not known")) {
            return ok("not a User");
        } else {
            return ok(answer);
        }
    }


    public Result updateUser(Http.Request update) {
        JsonNode json = update.body().asJson();
        String result = adapter.updateUser(json);
        return ok(result);

    }

    public Result findUser(String name) throws JsonProcessingException {
        JsonNode result = adapter.findUser(name);
        System.out.println(result);
        return ok(result);
    }


}

