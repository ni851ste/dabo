package persistence;

import org.javatuples.Decade;
import org.javatuples.Ennead;

import java.util.List;
import java.util.Optional;


public interface IUserPersistenceAdapter {
    Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>>>
    createUser(int id, Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>, List<Integer>, List<String>> data);

    Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>>
    getUserByID(int id);

    Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>>
    updateUser(int id,Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>> data);

    Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>>
    deleteUser(int id);

}
