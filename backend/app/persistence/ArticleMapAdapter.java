package persistence;

import org.apache.commons.collections4.CollectionUtils;
import org.javatuples.Octet;
import org.javatuples.Septet;
import org.javatuples.Triplet;

import java.util.*;

public class ArticleMapAdapter implements IArticlePersistenceAdapter
{
    Map<Integer, Septet<String, String, String, String, String, List<String>, List<String>>> savedArticles;

    public ArticleMapAdapter()
    {
        this.savedArticles = new HashMap<>();
    }

    @Override
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> createArticle(int id,
                                                                                                                Septet<String, String, String, String, String, List<String>, List<String>> data)
    {
        savedArticles.put(id, data);

        return Optional.of(new Octet<>(id, data.getValue0(), data.getValue1(), data.getValue2(), data.getValue3(), data.getValue4(), data.getValue5(), data.getValue6()));
    }

    @Override
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> getArticleById(int id)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        Septet<String, String, String, String, String, List<String>, List<String>> article = savedArticles.get(id);

        return Optional.of(
                new Octet<>(id,
                        article.getValue0(),
                        article.getValue1(),
                        article.getValue2(),
                        article.getValue3(),
                        article.getValue4(),
                        article.getValue5(),
                        article.getValue6()
                ));
    }

    @Override
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>>
    updateArticle(int id, Septet<String, String, String, String, String, List<String>, List<String>> data)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        savedArticles.put(id, data);

        return Optional.of(
                new Octet<>(id,
                        data.getValue0(),
                        data.getValue1(),
                        data.getValue2(),
                        data.getValue3(),
                        data.getValue4(),
                        data.getValue5(),
                        data.getValue6()));
    }

    @Override
    public Optional<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> deleteArticle(int id)
    {
        if (!savedArticles.containsKey(id))
        {
            return Optional.empty();
        }

        Septet<String, String, String, String, String, List<String>, List<String>> removedArticle = savedArticles.remove(id);

        return Optional.of(
                new Octet<>(id,
                        removedArticle.getValue0(),
                        removedArticle.getValue1(),
                        removedArticle.getValue2(),
                        removedArticle.getValue3(),
                        removedArticle.getValue4(),
                        removedArticle.getValue5(),
                        removedArticle.getValue6()));
    }

    @Override
    public List<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> filterArticles(
            //            String nameFilter, String locationFilter,
            List<String> categoryFilter)
    {
        List<Octet<Integer, String, String, String, String, String, List<String>, List<String>>> foundArticles = new ArrayList<>();

        if (categoryFilter.size() == 0)
        {
            savedArticles.entrySet()
                    .forEach(article -> {
                        foundArticles.add(new Octet<>(article.getKey(),
                                article.getValue().getValue0(),
                                article.getValue().getValue1(),
                                article.getValue().getValue2(),
                                article.getValue().getValue3(),
                                article.getValue().getValue4(),
                                article.getValue().getValue5(),
                                article.getValue().getValue6()));
                    });
        }
        else
        {
            //        List<Map.Entry<Integer, Quintet<String, String, String, String, List<String>>>> filteredArticles =
            savedArticles.entrySet()
                    .stream()
                    // Name Filter
                    //.filter(entry -> entry.getValue().getValue0().toLowerCase().contains(nameFilter.toLowerCase()))
                    // Location Filter
                    //.filter(entry -> entry.getValue().getValue3().contains(locationFilter))
                    // Filter for categories
                    .filter(entry -> CollectionUtils.containsAny(entry.getValue().getValue6(), categoryFilter))
                    .forEach(article -> {
                        foundArticles.add(new Octet<>(article.getKey(),
                                article.getValue().getValue0(),
                                article.getValue().getValue1(),
                                article.getValue().getValue2(),
                                article.getValue().getValue3(),
                                article.getValue().getValue4(),
                                article.getValue().getValue5(),
                                article.getValue().getValue6()));
                    });
        }

        return foundArticles;
    }

}
