package model.user;


import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.ok;


public class UserHttpAdapter {

    UserManagement adabter = new UserManagement();

    public Result createUserAdapter(){
        adabter.createUser("Test", "Test@mail.com", "Test2");
        return ok();
    }

    public Result deleteUserAdapter(){
        adabter.deleteUser(0);
        return ok();
    }

    public Result updateUserAdapter(){
        List<String> newList = new ArrayList<String>();
        newList.add("hallo");
        newList.add("Hallo@hallo.de");
        newList.add("Tschuess");

        adabter.updateUser(0, newList);
        return ok();
    }

}
