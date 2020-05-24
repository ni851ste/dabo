package model.article;

import java.util.*;


public class Article {
    Map<Integer, List<String>> articleMap = new HashMap<>();
    int globalIdCounter = 0;
    //TODO add   image, notAVailableDate,rating,category
    public int addArticle(String name, String description, String insertionDate,
                          String location ) {
        int localIdCounter = this.globalIdCounter;
        List<String> values = new ArrayList<>();
        values.add(name);
        values.add(description);
        values.add(insertionDate);
        values.add(location);
        articleMap.put(this.globalIdCounter, values);
        this.globalIdCounter += 1;
        return localIdCounter;
    }

    public boolean deleteArticle(int articleId) {
        if (articleMap.containsKey(articleId)) {
            articleMap.remove(articleId);
            return true;
        }
        return false;
    }
    //TODO
    // return : Artikle to Json
    public boolean showArticle(int articleId){
        if (articleMap.containsKey(articleId)) {
            articleMap.get(articleId);
            return true;
        }
        return false;
    }
}