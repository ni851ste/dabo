package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;


public class ArticleHttpAdapter
{
    ArticleManagement articleManagement = new ArticleManagement();

    public Result createArticle(Request request)
    {
        JsonNode json = request.body().asJson();

        List<String> categoryList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryList.add(node.asText()));

        Quintet<String, String, String, String, List<String>> toBeCreatedArticle =
                new Quintet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("location").asText(),
                        json.get("insertionDate").asText(),
                        categoryList);

        Sextet<Integer, String, String, String, String, List<String>> createdArticle = articleManagement.createArticle(toBeCreatedArticle);

        // TODO Use 6th value in sextet

        // creating an article can not fail to date
        // this will always go into the else case
        if (createdArticle.getValue0() == -1)
        {
            return badRequest();
        }
        else
        {
            JSONObject returnJson = new JSONObject()
                    .put("id", createdArticle.getValue0())
                    .put("name", createdArticle.getValue1())
                    .put("description", createdArticle.getValue2())
                    .put("insertionDate", createdArticle.getValue3())
                    .put("location", createdArticle.getValue4());

            createdArticle.getValue5().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result getArticle(int id)
    {
        Sextet<Integer, String, String, String, String, List<String>> article = articleManagement.getArticleById(id);

        // TODO use 6th value
        if (article.getValue0() == -1)
        {
            return badRequest();
        }
        else
        {
            return ok(
                    new JSONObject()
                            .put("id", article.getValue0())
                            .put("name", article.getValue1())
                            .put("description", article.getValue2())
                            .put("insertionDate", article.getValue3())
                            .put("location", article.getValue4())
                            .toString())
                    .as("application/json");
        }
    }

    public Result updateArticle(int id, Request request)
    {
        JsonNode json = request.body().asJson();

        Quintet<String, String, String, String, List<String>> toBeUpdatedArticle =
                new Quintet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("location").asText(),
                        json.get("insertionDate").asText(),
                        json.findValuesAsText("categories"));

        Sextet<Integer, String, String, String, String, List<String>> updatedArticle = articleManagement.updateArticle(id, toBeUpdatedArticle);

        // TODO use 6th value

        if (updatedArticle.getValue0() == -1)
        {
            return badRequest();
        }
        else
        {
            return ok(
                    new JSONObject()
                            .put("id", updatedArticle.getValue0())
                            .put("name", updatedArticle.getValue1())
                            .put("description", updatedArticle.getValue2())
                            .put("insertionDate", updatedArticle.getValue3())
                            .put("location", updatedArticle.getValue4())
                            .toString())
                    .as("application/json");
        }
    }

    public Result deleteArticle(int id)
    {
        Sextet<Integer, String, String, String, String, List<String>> deletedArticle = articleManagement.deleteArticle(id);

        // TODO use 6th value

        if (deletedArticle.getValue0() == -1)
        {
            return badRequest();
        }
        else
        {
            return ok(
                    new JSONObject()
                            .put("id", deletedArticle.getValue0())
                            .put("name", deletedArticle.getValue1())
                            .put("description", deletedArticle.getValue2())
                            .put("insertionDate", deletedArticle.getValue3())
                            .put("location", deletedArticle.getValue4())
                            .toString())
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

        String nameFilter = json.get("nameFilter").asText();
        String locationFilter = json.get("location").asText();


        List<String> categoryFilterList = new ArrayList<>();
        json.get("categories").forEach(node -> categoryFilterList.add(node.asText()));

        List<Sextet<Integer, String, String, String, String, List<String>>> filteredArticles = articleManagement.filterArticles(nameFilter, locationFilter, categoryFilterList);

        JSONArray foundArticles = new JSONArray();

        filteredArticles.forEach(article -> {
            foundArticles.put(new JSONObject()
                    .put("id", article.getValue0())
                    .put("name", article.getValue1())
                    .put("description", article.getValue2())
                    .put("insertionDate", article.getValue3())
                    .put("location", article.getValue4()));
        });

        return ok(foundArticles.toString())
                .as("application/json");

    }

}
