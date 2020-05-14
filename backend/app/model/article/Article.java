package model.article;

import java.util.*;


public class Article {
    Map<Integer, List<String>> article = new HashMap<>();
    int globalIdCounter = 0;
    //TODO add  Date[] notAvailableDate, image
    public boolean createArticle(String name, String description, String insertionDate, String notAvailableDate, String rating, String location, String category  ) {
        int localIdCounter = this.globalIdCounter;
        List<String> values = new ArrayList<>();
        values.add(name);
        values.add(description);
        values.add(insertionDate);
        values.add(notAvailableDate);
        values.add(rating);
        values.add(location);
        values.add(category);
        article.put(this.globalIdCounter, values);
        this.globalIdCounter += 1;
        return article.containsKey(localIdCounter);
    }

    public boolean deleteArticle(int userId) {
        if (article.containsKey(userId)) {
            article.remove(userId);
            return true;
        }
        return false;
    }
}