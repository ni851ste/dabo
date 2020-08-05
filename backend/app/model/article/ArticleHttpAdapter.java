package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.javatuples.Octet;
import org.javatuples.Septet;
import org.javatuples.Triplet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;



public class ArticleHttpAdapter {
    @Inject
    ArticleManagement articleManagement = new ArticleManagement();

    public Result createArticle(Request request) {
        JsonNode json = request.body().asJson();

        Triplet<String, String, String> locationTriplet = new Triplet<>(
                json.get("location").asText(),
                json.get("country").asText(),
                json.get("plz").asText());

        Triplet<String, String, String> timeTriplet = new Triplet<>(
                json.get("insertionDate").asText(),
                json.get("fromDate").asText(),
                json.get("toDate").asText());

        List<String> categoryList = new ArrayList<>();
        // TODO for demo purposes disabled categories
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        List<String> imagesList = new ArrayList<>();
        json.get("images").forEach(node -> imagesList.add(node.asText()));

        Septet<String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>> toBeCreatedArticle =
                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        timeTriplet,
                        locationTriplet,
                        json.get("userId").asText(),
                        imagesList,
                        categoryList);
        System.out.println(toBeCreatedArticle.toString());

        Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> createdArticle = articleManagement.createArticle(toBeCreatedArticle);


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
        Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> article = articleManagement.getArticleById(id);

        if (article.isEmpty()) {
            System.out.println("in Bad Request");
            return badRequest();
        } else {

            JSONObject returnJson = new JSONObject()
                    .put("id", article.get().getValue0())
                    .put("name", article.get().getValue1())
                    .put("description", article.get().getValue2())
                    .put("insertionDate", article.get().getValue3().getValue0())
                    .put("fromDate", article.get().getValue3().getValue1())
                    .put("toDate", article.get().getValue3().getValue2())
                    .put("location", article.get().getValue4().getValue0())
                    .put("country", article.get().getValue4().getValue1())
                    .put("plz", article.get().getValue4().getValue2())
                    .put("userId", article.get().getValue5());

            article.get().getValue6().forEach(images -> returnJson.append("images", images));
            article.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateArticle(int id, Request request) {
        JsonNode json = request.body().asJson();

        Triplet<String, String, String> locationTriplet = new Triplet<>(
                json.get("location").asText(),
                json.get("country").asText(),
                json.get("plz").asText());

        Triplet<String, String, String> timeTriplet = new Triplet<>(
                json.get("insertionDate").asText(),
                json.get("fromDate").asText(),
                json.get("toDate").asText());

        List<String> categoryList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        List<String> imagesList = new ArrayList<>();
        json.get("images").forEach(node -> imagesList.add(node.asText()));

        Septet< String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>> toBeUpdatedArticle =
                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        timeTriplet,
                        locationTriplet,
                        json.get("userId").asText(),
                        imagesList,
                        categoryList);

        Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> updatedArticle = articleManagement.updateArticle(id, toBeUpdatedArticle);


        if (updatedArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", updatedArticle.get().getValue0())
                    .put("name", updatedArticle.get().getValue1())
                    .put("description", updatedArticle.get().getValue2())
                    .put("insertionDate", updatedArticle.get().getValue3().getValue0())
                    .put("fromDate", updatedArticle.get().getValue3().getValue1())
                    .put("toDate", updatedArticle.get().getValue3().getValue2())
                    .put("location", updatedArticle.get().getValue4().getValue0())
                    .put("country", updatedArticle.get().getValue4().getValue1())
                    .put("plz", updatedArticle.get().getValue4().getValue2())
                    .put("userId", updatedArticle.get().getValue5());

            updatedArticle.get().getValue6().forEach(images -> returnJson.append("images", images));

            updatedArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result deleteArticle(int id) {
        Optional<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> deletedArticle = articleManagement.deleteArticle(id);
        if (deletedArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", deletedArticle.get().getValue0())
                    .put("name", deletedArticle.get().getValue1())
                    .put("description", deletedArticle.get().getValue2())
                    .put("insertionDate", deletedArticle.get().getValue3().getValue0())
                    .put("fromDate", deletedArticle.get().getValue3().getValue1())
                    .put("toDate", deletedArticle.get().getValue3().getValue2())
                    .put("location", deletedArticle.get().getValue4().getValue0())
                    .put("country", deletedArticle.get().getValue4().getValue1())
                    .put("plz", deletedArticle.get().getValue4().getValue2())
                    .put("userId", deletedArticle.get().getValue5());

            deletedArticle.get().getValue6().forEach(images -> returnJson.append("images", images));

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

        List<Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>>> filteredArticles = articleManagement.filterArticles(
                //                nameFilter, locationFilter,
                categoryFilterList);

        JSONArray foundArticles = new JSONArray();

        for (Octet<Integer, String, String, Triplet<String,String,String>, Triplet<String, String, String>,String, List<String>, List<String>> article : filteredArticles) {
            JSONObject foundArticleJson = new JSONObject();

            foundArticleJson.put("id", article.getValue0())
                    .put("name", article.getValue1())
                    .put("description", article.getValue2())
                    .put("insertionDate", article.getValue3().getValue0())
                    .put("fromDate", article.getValue3().getValue1())
                    .put("toDate", article.getValue3().getValue2())
                    .put("location", article.getValue4().getValue0())
                    .put("country", article.getValue4().getValue1())
                    .put("plz", article.getValue4().getValue2())
                    .put("userId", article.getValue5());

            article.getValue6().forEach(images -> foundArticleJson.append("images", images));
            article.getValue7().forEach(category -> foundArticleJson.append("categories", category));

            foundArticles.put(foundArticleJson);

        }

        return ok(foundArticles.toString())
                .as("application/json");

    }
    

}