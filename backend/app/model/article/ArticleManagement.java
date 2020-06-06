package model.article;

import org.javatuples.Quartet;
import org.javatuples.Quintet;
import persistence.ArticleMapAdapter;
import persistence.IArticlePersistenceAdapter;

import java.util.Optional;


public class ArticleManagement
{
    int globalIdCounter = 0;
    //TODO make this dependent on app.config var
    IArticlePersistenceAdapter database = new ArticleMapAdapter();


    // TODO add image, notAvailableDate, rating, category

    /**
     * @param data The data of the article that is requested to be created
     *             data[0]: Name
     *             data[1]: Description
     *             data[2]: Insertion Date
     *             data[3]: City - Location
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     */
    // TODO can this method fail?
    public Quintet<Integer, String, String, String, String> createArticle(Quartet<String, String, String, String> data)
    {
        int localIdCounter = this.globalIdCounter;

        // TODO do some basic checks of data is correct

        Optional<Quintet<Integer, String, String, String, String>> returnValue = database.createArticle(localIdCounter, data);

        this.globalIdCounter += 1;
        // Return value is never Optional.empty since this method does not fail to date
        return returnValue.orElseGet(() -> new Quintet<>(-1, "", "", "", ""));
    }

    /**
     * @param articleId Find article with given Id
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     */
    public Quintet<Integer, String, String, String, String> getArticleById(int articleId)
    {
        Optional<Quintet<Integer, String, String, String, String>> searchedArticle = database.getArticleById(articleId);
        return searchedArticle.orElseGet(() -> new Quintet<>(-1, "", "", "", ""));
    }

    /**
     * @param articleId to identify article that wants to be updated
     * @param data      data to update article
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     */
    public Quintet<Integer, String, String, String, String> updateArticle(int articleId, Quartet<String, String, String, String> data)
    {
        Optional<Quintet<Integer, String, String, String, String>> updatedArticle = database.updateArticle(articleId, data);
        return updatedArticle.orElseGet(() -> new Quintet<>(-1, "", "", "", ""));
    }

    /**
     * @param articleId to identify article that wants to be deleted
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     */
    public Quintet<Integer, String, String, String, String> deleteArticle(int articleId)
    {
        Optional<Quintet<Integer, String, String, String, String>> deletedArticle = database.deleteArticle(articleId);
        return deletedArticle.orElseGet(() -> new Quintet<>(-1, "", "", "", ""));
    }


}

