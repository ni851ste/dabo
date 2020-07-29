package persistence;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserMapAdapter implements IUserPersistenceAdapter
{
    Map<String, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
            savedUsers;


    public UserMapAdapter()
    {
        this.savedUsers = new HashMap<>();
    }

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String,String>>>
    createUser(String id, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>> data)
    {
        savedUsers.put(id, data);

        return Optional.of(new Decade<>(id, data.getValue0(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4(), data.getValue5(), data.getValue6(), data.getValue7(), data.getValue8()));
    }

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    getUserByID(String id)
    {
        if (!savedUsers.containsKey(id))
        {
            return Optional.empty();
        }

        Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>
                user = savedUsers.get(id);

        return Optional.of(
                new Decade<>(id, user.getValue0(), user.getValue1(), user.getValue2(), user.getValue3(), user.getValue4(), user.getValue5(), user.getValue6(), user.getValue7(), user.getValue8()));
    }

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    updateUser(String id, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> data)
    {

        if (!savedUsers.containsKey(id))
        {
            return Optional.empty();
        }

        savedUsers.put(id, data);

        return Optional.of(new Decade<>(id, data.getValue0(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4(), data.getValue5(), data.getValue6(), data.getValue7(), data.getValue8()));
    }

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    deleteUser(String id)
    {
        if (!savedUsers.containsKey(id))
        {
            return Optional.empty();
        }

        Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> removedUser = savedUsers.remove(id);

        return Optional.of(new Decade<>(id, removedUser.getValue0(), removedUser.getValue1(), removedUser.getValue2(), removedUser.getValue3(), removedUser.getValue4(), removedUser.getValue5(), removedUser.getValue6(), removedUser.getValue7(), removedUser.getValue8()));

    }

}
