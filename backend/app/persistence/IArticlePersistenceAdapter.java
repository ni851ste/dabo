package persistence;

import org.javatuples.Octet;
import org.javatuples.Septet;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistenceAdapter
{

    Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>>createArticle(int id,
                                                                                                                                                             Septet< String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>> data);

    Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> getArticleById(int id);

    Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> updateArticle(int id,
                                                                             Septet<String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>> data);

    Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> deleteArticle(int id);

    List<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> filterArticles(
//            String nameFilter, String locationFilter,
                                                                          List<String> categoryFilter);

}
