package model.article;

import play.mvc.Result;

import static play.mvc.Results.ok;

public class ArticleHttpAdapter {
    Article adabter = new Article();

    public Result createUserAdapter(){
        adabter.createArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "18.04.1993","5 Stars",
                "Berlin", "Sports & Leisure");
        return ok();
    }

    public Result deleteUserAdapter(){
        adabter.deleteArticle(0);
        return ok();
    }

}
