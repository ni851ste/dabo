package model.user;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;
import persistence.IUserPersistenceAdapter;
import persistence.UserMapAdapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserManagement
{
    IUserPersistenceAdapter database = new UserMapAdapter();

    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String,String>>>
    createUser(Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>> data) {

        String id = Integer.toString((data.getValue0() + " " + data.getValue1()).hashCode());

        Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>>>
                returnValue = database.createUser(id, data);

        return returnValue;
    }


    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    deleteUser(String userId)
    {
        return database.deleteUser(userId);
    }


    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    updateUser(String id, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> data)
    {
        return database.updateUser(id, data);
    }


    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    getUserByID(String userId)
    {
        return database.getUserByID(userId);
    }

    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    loginUser(String email, String password) {
        return database.findRequestedUserHash(email, password);
    }

}
