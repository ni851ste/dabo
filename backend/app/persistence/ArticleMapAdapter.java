package persistence;

import java.util.List;
import java.util.Map;

public class ArticleMapAdapter implements IArticleSavingAdapter
{
    Map<String, List<String>> articleCollection;

    public ArticleMapAdapter() {

    }

    @Override
    public int getArticleById(int id)
    {
        return 0;
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
