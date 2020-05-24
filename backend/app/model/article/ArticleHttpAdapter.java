package model.article;

import play.mvc.Result;

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
        return ok();
    }

}
