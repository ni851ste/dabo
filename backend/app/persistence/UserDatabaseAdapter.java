package persistence;

import com.mongodb.*;
import scala.Tuple6;

import java.util.Optional;

public class UserDatabaseAdapter 
//        implements IUserPersistenceAdapter
{
    DBCollection articleCollection;
    DBCollection userCollection;

    public UserDatabaseAdapter()
    {
        DB database = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))
                .getDB("dabo");


        this.userCollection = database.getCollection("dabo_user");
        this.userCollection = database.getCollection("dabo_user");

    }

    //    @Override
    public Optional<Tuple6<String, String, String, String, String, String>> getUserById(int id)
    {
        DBObject query = new BasicDBObject("_id", id);

        DBCursor cursor = this.userCollection.find(query);
        Optional<DBObject> queryResult = Optional.ofNullable(cursor.one());

        if (queryResult.isPresent())
        {
            System.out.println(queryResult.toString());
            DBObject qR = queryResult.get();

            return Optional.of(Tuple6.apply(qR.get("_id").toString(),
                    qR.get("name").toString(),
                    qR.get("description").toString(),
                    qR.get("image").toString(),
                    qR.get("location").toString(),
                    qR.get("insertionDate").toString()));
        }
        else
        {
            System.out.println("Query had no answers");
            return Optional.empty();
        }
    }

    //    @Override
    public boolean createUser(int id,
                                 String name,
                                 String description,
                                 String image,
                                 String location,
                                 String insertionDate)
    {

        // TODO does id need to be String or someting else too?
        //  Quite important when it comes to saving images
        DBObject user = new BasicDBObject("_id", id)
                .append("name", name)
                .append("description", description)
                .append("image", image)
                .append("location", location)
                .append("insertionDate", insertionDate);

        WriteResult result = this.userCollection.insert(user);

        return result.wasAcknowledged() && !result.isUpdateOfExisting();
    }

    //    @Override
    public boolean deleteUser(int id)
    {

        DBObject user = new BasicDBObject("_id", id);

        WriteResult result = this.userCollection.remove(user);

        return result.getN() > 0;
    }


    public static void main(String[] args)
    {
        // This main features a simple Use-Case of
        UserDatabaseAdapter userDatabaseAdapter = new UserDatabaseAdapter();

        System.out.println("Create");
        userDatabaseAdapter.createUser(1,
                "BierPong3",
                "guter Zustand",
                "image",
                "Berlin",
                "20.02.13");

        System.out.println("\nGet");
        userDatabaseAdapter.getUserById(1);

        System.out.println("\nDelete");
        userDatabaseAdapter.deleteUser(1);
        userDatabaseAdapter.deleteUser(2);

        System.out.println("\nGet after delete");
        userDatabaseAdapter.getUserById(1);
    }
}
