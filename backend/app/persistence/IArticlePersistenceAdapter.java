package persistence;

import org.javatuples.Quartet;
import org.javatuples.Quintet;
import scala.Tuple6;

import java.util.Optional;

public interface IArticlePersistenceAdapter
{
    Optional<Quintet<Integer, String, String, String, String>> createArticle(int id,
                                                                             Quartet<String, String, String, String> data);

    Optional<Quintet<Integer, String, String, String, String>> getArticleById(int id);

    Optional<Quintet<Integer, String, String, String, String>> updateArticle(int id,
                                                                             Quartet<String, String, String, String> data);

    Optional<Quintet<Integer, String, String, String, String>> deleteArticle(int id);

}
