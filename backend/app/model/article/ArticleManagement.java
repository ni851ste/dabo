package model.article;

import org.javatuples.Octet;
import org.javatuples.Septet;
import org.javatuples.Triplet;
import persistence.ArticleMapAdapter;
import persistence.IArticlePersistenceAdapter;

import java.util.List;
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
     *             data[4]: Categories List
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     * quintet[5]: Category List
     */
    // TODO can this method fail?
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>>
    createArticle(Septet<String, String, String, String, String, List<String>, List<String>> data)
    {
        int localIdCounter = this.globalIdCounter;

        // TODO do some basic checks of data is correct

        Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> returnValue = database.createArticle(localIdCounter, data);

        this.globalIdCounter += 1;
        // Return value is never Optional.empty since this method does not fail to date
        return returnValue;
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
     * quintet[5]: Category List
     */
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> getArticleById(int articleId)
    {
        Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> searchedArticle = database.getArticleById(articleId);
        return searchedArticle;
    }

    /**
     * @param articleId to identify article that wants to be updated
     * @param data      data to update article
     *                  data[0]: Name
     *                  data[1]: Description
     *                  data[2]: Insertion Date
     *                  data[3]: City - Location
     *                  data[4]: Categories List
     * @return if successful: return created article with all its data
     * if failed: returns empty Quintet with -1 as Id
     * quintet[0]: Id
     * quintet[1]: Name
     * quintet[2]: Description
     * quintet[3]: Insertion Date
     * quintet[4]: City - Location
     * quintet[5]: Category List
     */
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>>
    updateArticle(int articleId, Septet<String, String, String, String, String, List<String>, List<String>> data)
    {
        Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> updatedArticle =
                database.updateArticle(articleId, data);
        return updatedArticle;
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
     * quintet[5]: Category List
     */
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> deleteArticle(int articleId)
    {
        Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> deletedArticle = database.deleteArticle(articleId);
        return deletedArticle;
    }

    /**
     * @param nameFilter     String to filter article names
     * @param locationFilter String to filter article locations
     * @param categoryFilter List of Strings to filter categories
     * @return List of found articles that match the filter
     */
    public List<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> filterArticles(
            //            String nameFilter, String locationFilter,
            List<String> categoryFilter)
    {
        // TODO some empty field or error checks

        return database.filterArticles(
                //                nameFilter, locationFilter,
                categoryFilter);
    }


}

