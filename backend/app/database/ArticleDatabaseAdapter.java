package database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ArticleDatabaseAdapter
{
    public static void main(String[] args)
    {
        MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("test-dabo");
    }
}
