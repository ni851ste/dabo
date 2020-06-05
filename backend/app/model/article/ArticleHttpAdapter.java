package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;


import java.io.IOException;
import play.libs.concurrent.HttpExecutionContext;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;


public class ArticleHttpAdapter {
    Article adapter = new Article();

    private HttpExecutionContext httpExecutionContext;

    //TODO speichert artikel mit ""
    public Result addArticleAdapter(Http.Request request){

    JsonNode json = request.body().asJson();

    String name  = json.get("name").toString();
    String description = json.get("description").toString();
    String location = json.get("location").toString();
    String insertionDate = json.get("insertionDate").toString();



    int artikelID = adapter.addArticle(name, description, insertionDate, location);

        return ok("Artikel ID:" + artikelID );
    }

    public Result deleteArticleAdapter(int id){
        boolean request = adapter.deleteArticle(id);
        if(request)
            return ok("Artikle: " + id + " gelöscht");
        return ok("Artikle:" + id + " würde nicht gelöscht");
    }


   public Result showArticleAdapter(int id) throws IOException {

       JsonNode json = adapter.showArticle(id);

           return ok(json);
   }

   public Result updateArticleAdapter(int id,Http.Request request) throws IOException {
       JsonNode json = request.body().asJson();

       String name  = json.get("name").toString();
       String description = json.get("description").toString();
       String location = json.get("location").toString();
       String insertionDate = json.get("insertionDate").toString();

       JsonNode updateJson = adapter.updateArticle(id,name, description, insertionDate,location);
       return ok(updateJson);
   }

}
