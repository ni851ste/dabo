package persistence;

import scala.Tuple6;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArticleMapAdapter implements IArticleSavingAdapter
{
    Map<String, List<String>> articleCollection;

    public ArticleMapAdapter() {

    }

    @Override
    public Optional<Tuple6<String, String, String, String, String, String>> getArticleById(int id)
    {
        return null;
    }

    @Override
    public boolean createArticle(int id, String name, String description, String image, String location, String insertionDate)
    {
        return false;
    }

    @Override
    public boolean deleteArticle(int id)
    {
        return false;
    }
}
