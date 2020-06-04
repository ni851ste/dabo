package model.article;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.*;


import java.io.IOException;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;





public class ArticleHttpAdapter {
    Article adapter = new Article();

    private HttpExecutionContext httpExecutionContext;

    public Result addArticleAdapter(Http.Request request){
    System.out.println("request:" + request.toString());
    System.out.println("request" + request.body().asJson());
//    JsonNode json = request.body().asJson();
//    String name  = json.get("name").toString();
//    String description = json.get("description").toString();
//    String location = json.get("location").toString();
//    String insertionDate = json.get("insertionDate").toString();
//
//    int artikelID = adapter.addArticle(name, description, insertionDate, location);
        return ok("Artikel ID:" );
    }

    public Result deleteArticleAdapter(int id){
        boolean request = adapter.deleteArticle(id);
        if(request)
            return ok("Artikle: " + id + " gelöscht");
        return ok("Artikle:" + id + " würde nicht gelöscht");
    }


    public CompletionStage<Result> showArticleAdapter(int id) throws IOException {

        return adapter.showArticle(id)
                .thenApplyAsync(
                     answer -> {
                         return ok(answer);
                     },
                             httpExecutionContext.current());
    }

}
