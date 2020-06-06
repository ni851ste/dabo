package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.*;
import play.mvc.Result;

import java.io.IOException;

import static play.mvc.Results.ok;


public class ArticleHttpAdapter
{
    ArticleManagement articleManagement = new ArticleManagement();

    //TODO speichert artikel mit ""
    public Result createArticle(Request request)
    {

        JsonNode json = request.body().asJson();
        System.out.println("add Artikel");

        String name = json.get("name").toString();
        String description = json.get("description").toString();
        String location = json.get("location").toString();
        String insertionDate = json.get("insertionDate").toString();

        int articleId = articleManagement.addArticle(name, description, insertionDate, location);

        return ok("Artikel ID:" + articleId);
    }

    public Result deleteArticle(int id)
    {
        System.out.println(id);
        boolean request = articleManagement.deleteArticle(id);
        if (request)
            return ok("Artikle: " + id + " gelöscht");
        return ok("Artikle:" + id + " würde nicht gelöscht");
    }


    public Result getArticle(int id) throws IOException
    {

        JsonNode json = articleManagement.getArticle(id);


        return ok(json);
    }

    public Result updateArticle(int id, Request request) throws IOException
    {
        JsonNode json = request.body().asJson();


        String name = json.get("name").toString();
        String description = json.get("description").toString();
        String location = json.get("location").toString();
        String insertionDate = json.get("insertionDate").toString();

        JsonNode updateJson = articleManagement.updateArticle(id, name, description, insertionDate, location);
        return ok(updateJson);
    }

    //   public Result filterArticleAdapter(Http.Request request){
    //
    //        JsonNode json = request.body().asJson();
    //        JsonNode filterJson = adapter.
    //   }

}
