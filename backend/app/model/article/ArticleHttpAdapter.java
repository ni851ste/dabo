package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.json.JSONObject;
import org.springframework.util.MimeType;
import play.mvc.Http.Request;
import play.mvc.Result;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;


public class ArticleHttpAdapter
{
    ArticleManagement articleManagement = new ArticleManagement();

    public Result createArticle(Request request)
    {
        JsonNode json = request.body().asJson();

        Quartet<String, String, String, String> toBeCreatedArticle =
                new Quartet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("location").asText(),
                        json.get("insertionDate").asText());

        Quintet<Integer, String, String, String, String> createdArticle = articleManagement.createArticle(toBeCreatedArticle);

        // creating an article can not fail to date
        // this will always go into the else tree
        if (createdArticle.getValue0() == -1)
        {
            return badRequest();
        }
        else
        {
            return ok(
                    new JSONObject()
                            .put("id", createdArticle.getValue0())
                            .put("name", createdArticle.getValue1())
                            .put("description", createdArticle.getValue2())
                            .put("insertionDate", createdArticle.getValue3())
                            .put("location", createdArticle.getValue4())
                            .toString())
                    .as("text/json");
        }
    }

    public Result getArticle(int id)
    {
        Quintet<Integer, String, String, String, String> article = articleManagement.getArticleById(id);

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
                    .as("text/json");
        }
    }

    public Result updateArticle(int id, Request request)
    {
        JsonNode json = request.body().asJson();

        Quartet<String, String, String, String> toBeUpdatedArticle =
                new Quartet<>(json.get("name").asText(),
                        json.get("description").asText(),
                        json.get("location").asText(),
                        json.get("insertionDate").asText());

        Quintet<Integer, String, String, String, String> updatedArticle = articleManagement.updateArticle(id, toBeUpdatedArticle);

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
                    .as("text/json");
        }
    }

    public Result deleteArticle(int id)
    {
        Quintet<Integer, String, String, String, String> deletedArticle = articleManagement.deleteArticle(id);

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
                    .as("text/json");
        }
    }


    //   public Result filterArticleAdapter(Http.Request request){
    //
    //        JsonNode json = request.body().asJson();
    //        JsonNode filterJson = adapter.
    //   }

}
