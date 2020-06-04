package persistence;

public interface IArticleSavingAdapter
{
    int getArticleById(int id);


    boolean createArticle(int id,
                          String name,
                          String description,
                          String image,
                          String location,
                          String insertionDate);

    boolean deleteArticle(int id);

}
