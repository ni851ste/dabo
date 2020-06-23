package persistence;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IUserPersistenceAdapter {

    Optional<Decade<Integer, String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>,  Map<String,String>>>
    createUser(int id, Ennead<String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>, List<Integer>, Map<String,String>> data);

    Optional<Decade<Integer, String, String, Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    getUserByID(int id);

    Optional<Decade<Integer, String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    updateUser(int id,Ennead<String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>> data);

    Optional<Decade<Integer, String, String,Triplet<String,String,Boolean>, Integer, String, List<Integer>, List<Integer>,List<Integer>, Map<String, String>>>
    deleteUser(int id);

}
