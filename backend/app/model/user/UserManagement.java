package model.user;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import persistence.IUserPersistenceAdapter;
import persistence.UserMapAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserManagement {
    int globalIdCounter = 0;
    IUserPersistenceAdapter database = new UserMapAdapter();

    public Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
    createUser(Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>> data) {

        int localIdCounter = this.globalIdCounter;
        Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>> returnValue = database.createUser(localIdCounter, data);

        this.globalIdCounter +=1;
        return returnValue.orElseGet(() -> new Decade(-1,"","",new ArrayList<String>(),0,"",new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));
    }




    public Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
    deleteUser(int userId)
    {
        Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>> deletedUser = database.deleteUser(userId);

        return deletedUser.orElseGet(() -> new Decade(-1,"","",new ArrayList<String>(), 0 ,"",new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));

    }

    public Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
    updateUser(int id, Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>> data)
    {
        Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>> updatedUser = database.updateUser(id, data);
        return updatedUser.orElseGet(() -> new Decade(-1,"","",new ArrayList<String>(),new ArrayList<Integer>(),"",new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));


    }

    public Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
    getUserByID(int userId){

        Optional<Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>> searchedUser = database.getUserByID(userId);
        return searchedUser.orElseGet(() -> new Decade(-1,"","",new ArrayList<String>(),0,"",new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));

    }

}
