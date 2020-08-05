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

    /**
     * @param data The data of the article that is requested to be created
     *             data[0]: Email
     *             data[1]: Password (encoded)
     *             data[2]: first and last Name and is last name visible
     *             data[3]: user rating
     *             data[4]: Picture
     *             data[5]: ID's of articles that are offered
     *             data[6]: ID's of articles that are borrowed
     *             data[7]: ID's of articles that are pinned
     *             data[8]: Address
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: UserId
     * quintet[1]: Email
     * quintet[2]: Password(encoded)
     * quintet[3]: Name (first, last & is last name visible)
     * quintet[4]: User Rating
     * quintet[5]: Picture
     * quintet[6]: ID's of articles that are offered
     * quintet[7]: ID's of articles that are borrowed
     * quintet[8]: ID's of articles that are pinned
     * quintet[9]: Address
     */
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

    public Optional<String> loginUser(String email, String password) {
        return database.findRequestedUserHash(email, password);
    }

}
