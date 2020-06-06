package persistence;

import org.javatuples.Quartet;
import org.javatuples.Quintet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ArticleMapAdapter implements IArticlePersistenceAdapter
{
    Map<Integer, Quartet<String, String, String, String>> articleCollection;

    public ArticleMapAdapter()
    {
        this.articleCollection = new HashMap<>();
    }

    @Override
    public Optional<Quintet<Integer, String, String, String, String>> createArticle(int id,
                                                                                    Quartet<String, String, String, String> data)
    {
        articleCollection.put(id, data);

        return Optional.of(new Quintet<>(id, data.getValue0(), data.getValue1(), data.getValue2(), data.getValue3()));
    }

    @Override
    public Optional<Quintet<Integer, String, String, String, String>> getArticleById(int id)
    {
        if (!articleCollection.containsKey(id))
        {
            return Optional.empty();
        }

        Quartet<String, String, String, String> article = articleCollection.get(id);

        return Optional.of(
                new Quintet<>(id,
                        article.getValue0(),
                        article.getValue1(),
                        article.getValue2(),
                        article.getValue3()));
    }

    @Override
    public Optional<Quintet<Integer, String, String, String, String>> updateArticle(int id,
                                                                                    Quartet<String, String, String, String> data)
    {
        if (!articleCollection.containsKey(id))
        {
            return Optional.empty();
        }

        articleCollection.put(id, data);

        return Optional.of(
                new Quintet<>(id,
                        data.getValue0(),
                        data.getValue1(),
                        data.getValue2(),
                        data.getValue3()));
    }

    @Override
    public Optional<Quintet<Integer, String, String, String, String>> deleteArticle(int id)
    {
        if (!articleCollection.containsKey(id))
        {
            return Optional.empty();
        }

        Quartet<String, String, String, String> removedArticle = articleCollection.remove(id);

        return Optional.of(
                new Quintet<>(id,
                        removedArticle.getValue0(),
                        removedArticle.getValue1(),
                        removedArticle.getValue2(),
                        removedArticle.getValue3()));
    }
}
