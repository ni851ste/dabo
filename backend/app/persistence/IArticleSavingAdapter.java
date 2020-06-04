package persistence;

import scala.Tuple6;

import java.util.Optional;

public interface IArticleSavingAdapter
{
    Optional<Tuple6<String, String, String, String, String, String>> getArticleById(int id);


    boolean createArticle(int id,
                          String name,
                          String description,
                          String image,
                          String location,
                          String insertionDate);

    boolean deleteArticle(int id);

}
