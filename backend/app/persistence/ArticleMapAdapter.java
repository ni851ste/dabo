package persistence;

import org.javatuples.Quintet;
import org.javatuples.Sextet;

import java.util.*;

public class ArticleMapAdapter implements IArticlePersistenceAdapter
{
    Map<Integer, Quintet<String, String, String, String, List<String>>> savedArticles;

    public ArticleMapAdapter()
    {
        this.savedArticles = new HashMap<>();
    }

    @Override
    public Optional<Sextet<Integer, String, String, String, String, List<String>>> createArticle(int id,
                                                                                   Quintet<String, String, String, String, List<String>> data)
    {
        savedArticles.put(id, data);

        return Optional.of(new Sextet<>(id, data.getValue0(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4()));
    }

    @Override
    public Optional<Sextet<Integer, String, String, String, String, List<String>>> getArticleById(int id)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        Quintet<String, String, String, String, List<String>> article = savedArticles.get(id);

        return Optional.of(
                new Sextet<>(id,
                        article.getValue0(),
                        article.getValue1(),
                        article.getValue2(),
                        article.getValue3(),
                        article.getValue4()));
    }

    @Override
    public Optional<Sextet<Integer, String, String, String, String, List<String>>> updateArticle(int id,
                                                                                    Quintet<String, String, String, String, List<String>> data)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        savedArticles.put(id, data);

        return Optional.of(
                new Sextet<>(id,
                        data.getValue0(),
                        data.getValue1(),
                        data.getValue2(),
                        data.getValue3(),
                        data.getValue4()));
    }

    @Override
    public Optional<Sextet<Integer, String, String, String, String, List<String>>> deleteArticle(int id)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        Quintet<String, String, String, String, List<String>> removedArticle = savedArticles.remove(id);

        return Optional.of(
                new Sextet<>(id,
                        removedArticle.getValue0(),
                        removedArticle.getValue1(),
                        removedArticle.getValue2(),
                        removedArticle.getValue3(),
                        removedArticle.getValue4()));
    }

    @Override
    public List<Sextet<Integer, String, String, String, String, List<String>>> filterArticles(
//            String nameFilter, String locationFilter,
            List<String> categoryFilter)
    {
        List<Sextet<Integer, String, String, String, String, List<String>>> foundArticles = new ArrayList<>();

//        List<Map.Entry<Integer, Quintet<String, String, String, String, List<String>>>> filteredArticles =
                savedArticles.entrySet()
                        .stream()
                        // Name Filter
                        //.filter(entry -> entry.getValue().getValue0().toLowerCase().contains(nameFilter.toLowerCase()))
                        // Location Filter
                        //.filter(entry -> entry.getValue().getValue3().contains(locationFilter))
                        // Filter for categories
                        .filter(entry -> entry.getValue().getValue4().containsAll(categoryFilter))
                        .forEach(article -> {
                            foundArticles.add(new Sextet<>(article.getKey(),
                                    article.getValue().getValue0(),
                                    article.getValue().getValue1(),
                                    article.getValue().getValue2(),
                                    article.getValue().getValue3(),
                                    article.getValue().getValue4()));
                        });

        return foundArticles;
    }
}
