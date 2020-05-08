package modle.user;

import modle.UserInterface;

import javax.inject.Inject;
import java.lang.*;

class User implements UserInterface {
    String name0 = "Robin";
    String name1 = "Nik";
    String name2 = "Caroline";
    String name3 = "Julia";
    String name4 = "Anna";
    String name5 = "Helen";
    String name = "";

    public void setUsername(String name){
        this.name = name;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public int getUserAge() {
        return 0;
    }

    @Override
    public String getUserEmail() {
        return null;
    }

}
