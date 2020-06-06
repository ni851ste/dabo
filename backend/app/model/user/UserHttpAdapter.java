package model.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.ok;

public class UserHttpAdapter {

    UserManagement adabter = new UserManagement();

    public UserHttpAdapter() throws JsonProcessingException {
    }

    public Result createUserAdapter(Http.Request create) {
        JsonNode json = create.body().asJson();
        adabter.createUser(json.get("username").toString(), json.get("email").toString(), json.get("name").toString(), Integer.parseInt(json.get("age").textValue()));
        return ok("Username: "+ json.get("username").toString() + "\nName: " + json.get("name").toString() + "\nAge: " + Integer.parseInt(json.get("age").textValue()) + "\nemail: " + json.get("email").toString());
    }

    public Result deleteUserAdapter(int id) {
        String answer = adabter.deleteUser(id);
        if(answer.contains("User is not known")) {
            return ok("not a User");
        } else {
            return ok(answer);
        }
    }


    public Result updateUserAdapter(Http.Request update) {
        JsonNode json = update.body().asJson();
        String result = adabter.updateUser(json);
        return ok(result);

    }

    public Result findUserAdapter(String name) throws JsonProcessingException {
        JsonNode result = adabter.findUser(name);
        System.out.println(result);
        return ok(result);
    }


}

