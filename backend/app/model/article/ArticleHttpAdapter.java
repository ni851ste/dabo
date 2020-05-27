package model.article;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Controller;


import static play.mvc.Results.ok;


public class ArticleHttpAdapter {
    Article adapter = new Article();
//TODO
    // Json to Java
    public Result addArticleAdapter(){
        int artikleId = adapter.addArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "Berlin");
        return ok("Artikel ID:" + artikleId);
    }

    public Result deleteArticleAdapter(int id){
        boolean request = adapter.deleteArticle(id);
        if(request)
            return ok("Artikle: " + id + " gelöscht");
        return ok("Artikle:" + id + " würde nicht gelöscht");
    }

    public Result showArticleAdapter(int id){
        adapter.showArticle(id);
        String testArtikle = "{\n" +
                "  \"name\": \"Georg\",\n" +
                "  \"alter\": 47,\n" +
                "  \"verheiratet\": false,\n" +
                "  \"beruf\": null,\n" +
                "  \"kinder\": [\n" +
                "    {\n" +
                "      \"name\": \"Lukas\",\n" +
                "      \"alter\": 19,\n" +
                "      \"schulabschluss\": \"Gymnasium\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Lisa\",\n" +
                "      \"alter\": 14,\n" +
                "      \"schulabschluss\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return ok(testArtikle).as("application/json");
    }

}
