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
    int globalIdCounter = 0;
    IUserPersistenceAdapter database = new UserMapAdapter();

    public Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String,String>>>
    createUser(Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>> data)
    {

        int localIdCounter = this.globalIdCounter;

        Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>>>
                returnValue = database.createUser(localIdCounter, data);

        this.globalIdCounter +=1;

        return returnValue;
    }


    public Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    deleteUser(int userId)
    {
        return database.deleteUser(userId);
    }


    public Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    updateUser(int id, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> data)
    {
        return database.updateUser(id, data);
    }


    public Optional<Decade<Integer, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    getUserByID(int userId)
    {

        return database.getUserByID(userId);
    }

}
