package model.user;

import org.javatuples.*;
import persistence.IUserPersistenceAdapter;
import persistence.UserMapAdapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserManagement
{
    IUserPersistenceAdapter database = new UserMapAdapter();
    int globalRatingCounter = 0;

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

    public Optional<Sextet<Integer, String, String, String, String, String>> ratingUser(Quintet<String, String, String, String, String> ratingQuintet)
    {
        int localIdCounter = this.globalRatingCounter;

        // TODO do some basic checks of data is correct

        Optional<Sextet<Integer, String, String, String, String, String>> returnValue = database.ratingUser(localIdCounter, ratingQuintet);

        this.globalRatingCounter += 1;
        // Return value is never Optional.empty since this method does not fail to date
        return returnValue;
    }

    public List<Sextet<Integer,String,String,String,String,String>> filterRatings(String userId){
        return database.filterRatings(userId);
    }


}
