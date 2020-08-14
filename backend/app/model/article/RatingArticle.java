package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Sextet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static play.mvc.Results.ok;

public class RatingArticle {

    int globalIdCounter = 0;
    ArrayList<Sextet<Integer,String,String,String,String,String>> ratings= new ArrayList<>();

    public Result ratingArticle(Request request){

        int localIdCounter = this.globalIdCounter;

        JsonNode json = request.body().asJson();

Sextet<Integer,String,String,String,String,String> ratingSextet = new Sextet<>(
        localIdCounter,
        json.get("articleId").asText(),
        json.get("amountOfStars").asText(),
        json.get("text").asText(),
        json.get("author").asText(),
        json.get("date").asText())
        ;

        this.globalIdCounter += 1;

        ratings.add(ratingSextet);

        return ok();
    }

    public Result filterRatings(String articleId){

        List<Sextet<Integer,String,String,String,String,String>> ratingforArticleList =
                ratings.stream()
                .filter(node -> node.getValue1().equals(articleId))
                .collect(Collectors.toList());

        JSONArray ratingArrayJson = new JSONArray();

        for (Sextet<Integer,String,String,String,String,String> rating : ratingforArticleList ) {
            JSONObject ratingJson = new JSONObject();
            ratingJson.put("id" , rating.getValue0())
                    .put("articleId",rating.getValue1())
                    .put("amountOfStars",rating.getValue2())
                    .put("text",rating.getValue3())
                    .put("author",rating.getValue4())
                    .put("date",rating.getValue5())
                    ;

            ratingArrayJson.put(ratingJson);

        }

        return ok(ratingArrayJson.toString())
                .as("application/json");
    }



}
