package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.javatuples.Octet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;



public class ArticleHttpAdapter
{
    @Inject
    ArticleManagement articleManagement = new ArticleManagement();

    public Result createArticle(Request request)
    {
        JsonNode json = request.body().asJson();

        List<String> categoryList = new ArrayList<>();
        // TODO for demo purposes disabled categories
                json.get("categories").forEach(node -> categoryList.add(node.asText()));

        List<String> imagesList = new ArrayList<>();
        json.get("images").forEach(node -> imagesList.add(node.asText()));


        Septet<String, String, String, String, String, List<String>, List<String>> toBeCreatedArticle =
                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("insertionDate").asText(),
                        json.get("location").asText(),
                        json.get("userId").asText(),
                        imagesList,
                        categoryList);
        System.out.println(toBeCreatedArticle.toString());

        Optional<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> createdArticle = articleManagement.createArticle(toBeCreatedArticle);


        // creating an article can not fail to date
        // this will always go into the else case
        if (createdArticle.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", createdArticle.get().getValue0())
                    .put("name", createdArticle.get().getValue1())
                    .put("description", createdArticle.get().getValue2())
                    .put("insertionDate", createdArticle.get().getValue3())
                    .put("location", createdArticle.get().getValue4())
                    .put("userId",createdArticle.get().getValue5())
                    .put("images",createdArticle.get().getValue6());

            createdArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result getArticle(int id)
    {
        Optional<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> article = articleManagement.getArticleById(id);

        if (article.isEmpty())
        {
            System.out.println("in Bad Request");
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", article.get().getValue0())
                    .put("name", article.get().getValue1())
                    .put("description", article.get().getValue2())
                    .put("insertionDate", article.get().getValue3())
                    .put("location", article.get().getValue4())
                    .put("userId",article.get().getValue5());


            article.get().getValue6().forEach(images -> returnJson.append("images", images));
            article.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result updateArticle(int id, Request request)
    {
        JsonNode json = request.body().asJson();

        List<String> categoryList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        List<String> imagesList = new ArrayList<>();
        json.get("images").forEach(node -> imagesList.add(node.asText()));

        Septet<String, String, String, String,String,List<String>, List<String>> toBeUpdatedArticle =

                new Septet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("insertionDate").asText(),
                        json.get("location").asText(),
                        json.get("userId").asText(),
                        imagesList,
                        categoryList);

        Optional<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> updatedArticle =
                articleManagement.updateArticle(id, toBeUpdatedArticle);


        if (updatedArticle.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", updatedArticle.get().getValue0())
                    .put("name", updatedArticle.get().getValue1())
                    .put("description", updatedArticle.get().getValue2())
                    .put("insertionDate", updatedArticle.get().getValue3())
                    .put("location", updatedArticle.get().getValue4())
                    .put("userId",updatedArticle.get().getValue5());

            updatedArticle.get().getValue6().forEach(images -> returnJson.append("images", images));
            updatedArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result deleteArticle(int id)
    {
        Optional<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> deletedArticle =
                articleManagement.deleteArticle(id);

        if (deletedArticle.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", deletedArticle.get().getValue0())
                    .put("name", deletedArticle.get().getValue1())
                    .put("description", deletedArticle.get().getValue2())
                    .put("insertionDate", deletedArticle.get().getValue3())
                    .put("location", deletedArticle.get().getValue4())
                    .put("userId",deletedArticle.get().getValue5());

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
    public Result filterArticles(Request request)
    {
        JsonNode json = request.body().asJson();

        //        String nameFilter = json.get("nameFilter").asText();
        //        String locationFilter = json.get("location").asText();


        List<String> categoryFilterList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryFilterList.add(node.asText()));

        List<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> filteredArticles = articleManagement.filterArticles(
                //                nameFilter, locationFilter,
                categoryFilterList);

        JSONArray foundArticles = new JSONArray();

        for (Octet<Integer, String, String, String, String, String,List<String>, List<String>> article : filteredArticles)
        {
            JSONObject foundArticleJson = new JSONObject();

            foundArticleJson.put("id", article.getValue0())
                    .put("name", article.getValue1())
                    .put("description", article.getValue2())
                    .put("insertionDate", article.getValue3())
                    .put("location", article.getValue4())
                    .put("userId",article.getValue5());


            article.getValue6().forEach(images -> foundArticleJson.append("images", images));
            article.getValue7().forEach(category -> foundArticleJson.append("categories", category));

            foundArticles.put(foundArticleJson);

        }

        return ok(foundArticles.toString())
                .as("application/json");

    }

    public Result ratingArticle(Request request){

        System.out.println("in rating Article");

        System.out.println(request.body().asJson().toString());

        JsonNode json = request.body().asJson();

        Quintet<String,String,String,String,String> ratingQuintet = new Quintet<>(
                json.get("id").asText(), //is articleID
                json.get("amountOfStars").asText(),
                json.get("comment").asText(),
                json.get("author").asText(),
                json.get("date").asText())
                ;
        Optional<Sextet<Integer,String,String,String,String,String>> rating = articleManagement.ratingArticle(ratingQuintet);

        if (rating.isEmpty())
        {
            return badRequest();
        }
        else
        {
            JSONObject ratingJson = new JSONObject();
            ratingJson.put("id" , rating.get().getValue0())
                    .put("articleId",rating.get().getValue1())
                    .put("amountOfStars",rating.get().getValue2())
                    .put("comment",rating.get().getValue3())
                    .put("author",rating.get().getValue4())
                    .put("date",rating.get().getValue5())
            ;


            return ok(ratingJson.toString())
                    .as("application/json");
        }
    }

    public Result filterRatings(String articleId){

        List<Sextet<Integer,String,String,String,String,String>> ratingArticleList = articleManagement.filterRatings(articleId);

        JSONArray ratingArrayJson = new JSONArray();

        for (Sextet<Integer,String,String,String,String,String> rating : ratingArticleList ) {
            JSONObject ratingJson = new JSONObject();
            ratingJson.put("id" , rating.getValue0())
                    .put("articleId",rating.getValue1())
                    .put("amountOfStars",rating.getValue2())
                    .put("comment",rating.getValue3())
                    .put("author",rating.getValue4())
                    .put("date",rating.getValue5())
            ;

            ratingArrayJson.put(ratingJson);

        }

        return ok(ratingArrayJson.toString())
                .as("application/json");
    }


}
