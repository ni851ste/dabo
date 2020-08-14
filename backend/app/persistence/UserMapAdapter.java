package persistence;

import org.javatuples.*;

import java.util.*;

public class UserMapAdapter implements IUserPersistenceAdapter
{
    Map<String, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
            savedUsers;

    Map<Integer,Quintet<String,String,String,String,String>> ratings;



    public UserMapAdapter()
    {
        this.savedUsers = new HashMap<>();
        this.ratings = new HashMap<>();

    }

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    createUser(String id, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> data)
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

    @Override
    public Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>
    findRequestedUserHash(String email, String password)
    {
        Optional<Map.Entry<String, Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>>> foundUser = savedUsers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getValue0().equals(email))
                .filter(entry -> entry.getValue().getValue1().equals(password))
                .findFirst();

        if (foundUser.isPresent())
        {
            Ennead<String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> user = foundUser.get().getValue();
            Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>> fullUserWithId =
                    new Decade<>(foundUser.get().getKey(),
                            user.getValue0(),
                            user.getValue1(),
                            user.getValue2(),
                            user.getValue3(),
                            user.getValue4(),
                            user.getValue5(),
                            user.getValue6(),
                            user.getValue7(),
                            user.getValue8());
            return Optional.of(fullUserWithId);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Sextet<Integer,String,String,String,String,String>> ratingUser(int id,
                                                                                      Quintet<String,String,String,String,String> ratingQuintet)
    {
        ratings.put(id ,ratingQuintet);

        return Optional.of(new Sextet<>(id, ratingQuintet.getValue0(), ratingQuintet.getValue1(), ratingQuintet.getValue2(), ratingQuintet.getValue3(), ratingQuintet.getValue4()));
    }
    @Override
    public List<Sextet<Integer,String,String,String,String,String>> filterRatings(String userId){

        List<Sextet<Integer,String,String,String,String,String>> ratingUserList = new ArrayList<>();


        ratings.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getValue0().contains(userId))
                .forEach(article -> {
                    ratingUserList.add(new Sextet<>(article.getKey(),
                            article.getValue().getValue0(),
                            article.getValue().getValue1(),
                            article.getValue().getValue2(),
                            article.getValue().getValue3(),
                            article.getValue().getValue4()));

                });


        return ratingUserList;
    }

}
