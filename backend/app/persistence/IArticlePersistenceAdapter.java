package persistence;

import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistenceAdapter
{
    Optional<Sextet<Integer, String, String, String, String, List<String>>> createArticle(int id,
                                                                             Quintet<String, String, String, String, List<String>> data);

    Optional<Sextet<Integer, String, String, String, String, List<String>>> getArticleById(int id);

    Optional<Sextet<Integer, String, String, String, String, List<String>>> updateArticle(int id,
                                                                             Quintet<String, String, String, String, List<String>> data);

    Optional<Sextet<Integer, String, String, String, String, List<String>>> deleteArticle(int id);

    List<Sextet<Integer, String, String, String, String, List<String>>> filterArticles(
//            String nameFilter, String locationFilter,
                                                                          List<String> categoryFilter);

}
