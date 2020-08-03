package persistence;

import org.javatuples.Octet;
import org.javatuples.Septet;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistenceAdapter
{
    Optional<Octet<Integer, String, String, String, String,String,String, List<String>>> createArticle(int id,
                                                                                                       Septet<String, String, String, String,String,String, List<String>> data);

    Optional<Octet<Integer, String, String, String, String,String,String, List<String>>> getArticleById(int id);

    Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> updateArticle(int id,
                                                                             Septet<String, String, String, String, String, String, List<String>> data);

    Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> deleteArticle(int id);

    List<Octet<Integer, String, String, String, String, String, String, List<String>>> filterArticles(
//            String nameFilter, String locationFilter,
                                                                          List<String> categoryFilter);

}
