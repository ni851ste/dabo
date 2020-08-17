package model.article;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.javatuples.Octet;
import org.javatuples.Septet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.*;


public class ArticleHttpAdapter {
    @Inject
    ArticleManagement articleManagement = new ArticleManagement();

    int articleIdCounter = 0;

    public Result createArticle(Request request) throws IOException, InterruptedException {
        JsonNode json = request.body().asJson();


        if (!json.has("sessionCookie")) {
            return unauthorized();
        }
        String sessionCook = json.get("sessionCookie").asText();
        if (sessionCook == null || sessionCook.equals("")) {
//            return badRequest("No user added");
            return unauthorized();
        }

        //TODO check how to compare if there is a User with the UserID equal to the sessionCookie


        String id = json.get("sessionCookie").asText();
        List<String> categoryList = new ArrayList<>();
        // TODO for demo purposes disabled categories
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        Septet<String, String, String, String, String, String, List<String>> toBeCreatedArticle =
                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("insertionDate").asText(),
                        json.get("location").asText(),
                        id,
                        json.get("images").asText(),
                        categoryList);

        Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> createdArticle = articleManagement.createArticle(toBeCreatedArticle);


        URL user = new URL("http://localhost:9000/user/updateArticleID/" + id + "/" + articleIdCounter);
        URLConnection uc = user.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        uc.getInputStream()));

        in.close();

        articleIdCounter += 1;




        // creating an article can not fail to date
        // this will always go into the else case
        if (createdArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", createdArticle.get().getValue0())
                    .put("name", createdArticle.get().getValue1())
                    .put("description", createdArticle.get().getValue2())
                    .put("insertionDate", createdArticle.get().getValue3())
                    .put("location", createdArticle.get().getValue4())
                    .put("userId", createdArticle.get().getValue5())
                    .put("images", createdArticle.get().getValue6());

            createdArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));


            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result getArticle(int id) {
        Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> article = articleManagement.getArticleById(id);

        if (article.isEmpty()) {
            System.out.println("in Bad Request");
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", article.get().getValue0())
                    .put("name", article.get().getValue1())
                    .put("description", article.get().getValue2())
                    .put("insertionDate", article.get().getValue3())
                    .put("location", article.get().getValue4())
                    .put("userId", article.get().getValue5())
                    .put("images", article.get().getValue6());


            article.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateArticle(int id, Request request) {
        JsonNode json = request.body().asJson();

        if (json.get("sessionCookie").isNull()) {
            return unauthorized();
        }
        Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> article = articleManagement.getArticleById(id);
        if (article.isEmpty()) {
            return badRequest("no article found for Update");
        }
        String value = article.get().getValue(5).toString();
        if (!value.equals(json.get("sessionCookie").asText())) {
            return badRequest("wrong User");
        }


        List<String> categoryList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        Septet<String, String, String, String, String, String, List<String>> toBeUpdatedArticle =
                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("insertionDate").asText(),
                        json.get("location").asText(),
                        json.get("sessionCookie").asText(),
                        json.get("images").asText(),
                        categoryList);

        Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> updatedArticle = articleManagement.updateArticle(id, toBeUpdatedArticle);


        if (updatedArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", updatedArticle.get().getValue0())
                    .put("name", updatedArticle.get().getValue1())
                    .put("description", updatedArticle.get().getValue2())
                    .put("insertionDate", updatedArticle.get().getValue3())
                    .put("location", updatedArticle.get().getValue4())
                    .put("userId", updatedArticle.get().getValue5())
                    .put("images", updatedArticle.get().getValue6());


            updatedArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result deleteArticle(int id) {
        Optional<Octet<Integer, String, String, String, String, String, String, List<String>>> deletedArticle = articleManagement.deleteArticle(id);
        if (deletedArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", deletedArticle.get().getValue0())
                    .put("name", deletedArticle.get().getValue1())
                    .put("description", deletedArticle.get().getValue2())
                    .put("insertionDate", deletedArticle.get().getValue3())
                    .put("location", deletedArticle.get().getValue4())
                    .put("userId", deletedArticle.get().getValue5())
                    .put("images", deletedArticle.get().getValue6());


            deletedArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }


    /**
     * Example JSON that may be expected.
     * {
     * "nameFilter": "Tisch",
     * "location": "Konstanz",
     * "categories": [ "household", "tools" ]
     * }
     */
    public Result filterArticles(Request request) {
        JsonNode json = request.body().asJson();

        //        String nameFilter = json.get("nameFilter").asText();
        //        String locationFilter = json.get("location").asText();


        List<String> categoryFilterList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryFilterList.add(node.asText()));

        List<Octet<Integer, String, String, String, String, String, String, List<String>>> filteredArticles = articleManagement.filterArticles(
                //                nameFilter, locationFilter,
                categoryFilterList);

        JSONArray foundArticles = new JSONArray();

        for (Octet<Integer, String, String, String, String, String, String, List<String>> article : filteredArticles) {
            JSONObject foundArticleJson = new JSONObject();

            foundArticleJson.put("id", article.getValue0())
                    .put("name", article.getValue1())
                    .put("description", article.getValue2())
                    .put("insertionDate", article.getValue3())
                    .put("location", article.getValue4())
                    .put("userId", article.getValue5())
                    .put("images", article.getValue6())
            ;

            article.getValue7().forEach(category -> foundArticleJson.append("categories", category));

            foundArticles.put(foundArticleJson);

        }

        return ok(foundArticles.toString())
                .as("application/json");

    }

}
