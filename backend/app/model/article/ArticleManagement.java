package model.article;

import org.javatuples.Octet;
import org.javatuples.Quartet;
import org.javatuples.Septet;
import persistence.ArticleMapAdapter;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public class ArticleManagement
{
    int articleIdCounter = 0;
    int requestIdCounter = 0;

    ArticleMapAdapter database = new ArticleMapAdapter();


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
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>>
    createArticle(Septet<String, String, String, String, String, List<String>, List<String>> data)
    {
        int localIdCounter = this.articleIdCounter;

        Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> returnValue = database.createArticle(localIdCounter, data);

        this.articleIdCounter += 1;
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

    public boolean acceptBorrowRequest(int requestId, int articleId)
    {
        if (!database.articleCanBeBorrowed(articleId)) return false;

        return database.acceptBorrowRequest(requestId, articleId);
    }


    public boolean isArticleBorrowed(int articleId)
    {
        return !database.articleCanBeBorrowed(articleId);
    }


    public boolean requestArticle(int articleId, String borrowingUser, Date untilDate)
    {
        boolean successful = database.requestArticle(requestIdCounter, articleId, borrowingUser, untilDate);
        requestIdCounter++;
        return successful;
    }


    List<Quartet<Integer, Integer, String, Date>> listRequestsForArticle(int articleId)
    {
        return database.listRequestsForArticle(articleId);
    }


}

