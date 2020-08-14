package persistence;

import org.javatuples.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IUserPersistenceAdapter {

    Optional<Decade<String, String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>>>
    createUser(String id, Ennead<String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String,String>> data);

    Optional<Decade<String, String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    getUserByID(String id);


    Optional<Decade<String, String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    updateUser(String id,Ennead<String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>> data);

    Optional<Decade<String, String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    deleteUser(String id);

    Optional<Decade<String, String, String, Triplet<String, String, Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String, String>>> findRequestedUserHash(String email, String password);

    Optional<Sextet<Integer,String,String,String,String,String>> ratingUser(int id,
                                                                               Quintet<String,String,String,String,String> ratingQuintet);

    List<Sextet<Integer,String,String,String,String,String>> filterRatings(String userId);


}
