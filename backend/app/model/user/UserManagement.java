package model.user;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import persistence.IUserPersistenceAdapter;
import persistence.UserMapAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserManagement {
//    Map<Integer, List<String>> users = new HashMap<>();
    int globalIdCounter = 0;
    IUserPersistenceAdapter database = new UserMapAdapter();

    public Decade<Integer, String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
    createUser(Ennead<String, String, List<String>, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>> data) {
    //Uuid: Int
//Email: String
//Passwort: String
//AnzeigeName: String
//Bewertungen: Rating[]
//Profilbild: ???
//Artikel zum verleihen: Int[]  (-> id)
//Ausgeliehene Artikel: Int[] (-> id)
//Gepinnte Artikel: Int[] (-> id)return returnValue.orElseGet(() -> new Sextet<>(-1, "", "", "", "", new ArrayList<>()));
//Adresse: Address
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

//    public Decade<Integer, String, String, String, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>
//    getUser(String name){
//
//        Optional<Decade<Integer, String, String, String, Integer, String, List<Integer>, List<Integer>,List<Integer>, List<String>>> filteredUser = database.filterUser(name);
//        return filteredUser.orElseGet(() -> new Decade(-1,"","","",0,"",new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<String>()));
//    }
//
//    public JsonNode findUser(String tofind) throws JsonProcessingException {
//        System.out.println(tofind);
//                new Sextet<>(id,
//                        user.getValue0(),
//                        user.getValue1(),
//                        user.getValue2(),
//                        user.getValue3(),
//                        user.getValue4())
//        if (!users.isEmpty()) {
//            for (int i = 0; i < globalIdCounter; i++) {
//                List<String> search = users.get(i);
//                for (String str : search) {
//                    if (str.substring(1, str.length() - 1).equals(tofind)) {
//                        String username = search.get(0);
//                        String email = search.get(1);
//                        String name = search.get(2);
//                        String age = search.get(3);
//                        String json = "{\"id\":" + i +
//                                ",\"username\":" + username +
//                                ",\"email\":" + email +
//                                ",\"name\":" + name +
//                                ",\"age\":" + age + "}";
//
//                        // create object mapper instance
//                        ObjectMapper mapper = new ObjectMapper();
//                        // convert JSON string to `JsonNode`
//                        //System.out.println(mapper);
//                        //System.out.println(json);
//                        JsonNode node = mapper.readTree(json);
//
//                        return node;
//
//                    }
//                }
//            }
//        }
//        // JSON string
//        String json = "{\"message\":\"User not found\"}";
//        // create object mapper instance
//        ObjectMapper mapper = new ObjectMapper();
//        // convert JSON string to `JsonNode`
//        JsonNode node1 = mapper.readTree(json);
//        //System.out.println(node1);
//        return node1;
//    }

}
