package modle.user;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void aNewUsernameShouldBeSet(){
        User tester = new User();

        String name = "Herbert";    //Name of a new User

        tester.setUsername(name);
        assertEquals("Herbert", tester.getUsername(), "Username should be Herbert");
        name = "Gisela";
        tester.setUsername(name);
        assertEquals("Gisela", tester.getUsername(), "Username should be Gisela");
    }
}