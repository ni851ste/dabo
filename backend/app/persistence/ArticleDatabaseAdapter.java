package persistence;

import com.mongodb.*;
import scala.Tuple6;

import java.util.Optional;

public class ArticleDatabaseAdapter implements IArticleSavingAdapter
{
    DBCollection articleCollection;
    DBCollection userCollection;

    public ArticleDatabaseAdapter()
    {
        DB database = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))
                .getDB("dabo");


        this.articleCollection = database.getCollection("dabo_article");
        this.userCollection = database.getCollection("dabo_user");

    }

    @Override
    public Optional<Tuple6<String, String, String, String, String, String>> getArticleById(int id)
    {
        DBObject query = new BasicDBObject("_id", id);

        DBCursor cursor = this.articleCollection.find(query);
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

    @Override
    public boolean createArticle(int id,
                                 String name,
                                 String description,
                                 String image,
                                 String location,
                                 String insertionDate)
    {

        // TODO does id need to be String or someting else too?
        //  Quite important when it comes to saving images
        DBObject article = new BasicDBObject("_id", id)
                .append("name", name)
                .append("description", description)
                .append("image", image)
                .append("location", location)
                .append("insertionDate", insertionDate);

        WriteResult result = this.articleCollection.insert(article);

        return result.wasAcknowledged() && !result.isUpdateOfExisting();
    }

    @Override
    public boolean deleteArticle(int id)
    {

        DBObject article = new BasicDBObject("_id", id);

        WriteResult result = this.articleCollection.remove(article);

        return result.getN() > 0;
    }


    public static void main(String[] args)
    {
        // This main features a simple Use-Case of
        ArticleDatabaseAdapter articleDatabaseAdapter = new ArticleDatabaseAdapter();

        System.out.println("Create");
        articleDatabaseAdapter.createArticle(1,
                "BierPong3",
                "guter Zustand",
                "image",
                "Berlin",
                "20.02.13");

        System.out.println("\nGet");
        articleDatabaseAdapter.getArticleById(1);

        System.out.println("\nDelete");
        articleDatabaseAdapter.deleteArticle(1);
        articleDatabaseAdapter.deleteArticle(2);

        System.out.println("\nGet after delete");
        articleDatabaseAdapter.getArticleById(1);
    }

}
