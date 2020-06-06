package model.article;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


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

    public JsonNode showArticle(int articleId) throws IOException {
        if (articleMap.containsKey(articleId)) {

          String name = articleMap.get(articleId).get(0);
          String description = articleMap.get(articleId).get(1);
          String insertionDate = articleMap.get(articleId).get(2);
          String location = articleMap.get(articleId).get(3);


            String input = "{" +
                    "\"name\": "+name+"," +
                    "\"description\": "+description+", " +
                    "\"location\": "+location+"," +
                    "\"insertionDate\": "+insertionDate+"" +
                    "}";

            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser jp = factory.createParser(input);
            JsonNode jsonNode = mapper.readTree(jp);

            return jsonNode;
        }
        return null;
    }

    public JsonNode updateArticle(int articleId,String name, String description, String insertionDate,
                                  String location) throws IOException {
        if (articleMap.containsKey(articleId)) {
            List<String> values = new ArrayList<>();
            values.add(name);
            values.add(description);
            values.add(insertionDate);
            values.add(location);
            articleMap.replace(articleId,values);

            String input = "{" +
                    "\"name\": "+name+"," +
                    "\"description\": "+description+", " +
                    "\"location\": "+location+"," +
                    "\"insertionDate\": "+insertionDate+"" +
                    "}";
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser jp = factory.createParser(input);
            JsonNode jsonNode = mapper.readTree(jp);

            return jsonNode;
        }
        System.out.println("hat keinen Artikel");
        return null;
    }


}

