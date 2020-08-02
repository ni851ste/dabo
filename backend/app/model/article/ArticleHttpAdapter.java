package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import org.javatuples.Octet;
import org.javatuples.Septet;
import org.javatuples.Triplet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.libs.Files.TemporaryFile;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;



public class ArticleHttpAdapter {
    @Inject
    ArticleManagement articleManagement = new ArticleManagement();

    public Result createArticle(Request request) {
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
        if (createdArticle.isEmpty()) {
            return badRequest();
        } else {
            JSONObject returnJson = new JSONObject()
                    .put("id", createdArticle.get().getValue0())
                    .put("name", createdArticle.get().getValue1())
                    .put("description", createdArticle.get().getValue2())
                    .put("insertionDate", createdArticle.get().getValue3())
                    .put("location", createdArticle.get().getValue4())
                    .put("userId", createdArticle.get().getValue5())
                    .put("images", createdArticle.get().getValue6());

            createdArticle.get().getValue7().forEach(category -> returnJson.append("categories", category));

            return ok(returnJson.toString())
                    .as("application/json");
        }
    }

    public Result getArticle(int id)
    {
        Optional<Octet<Integer, String, String, String, String,String,List<String>, List<String>>> article = articleManagement.getArticleById(id);

        if (article.isEmpty()) {
            System.out.println("in Bad Request");
            return badRequest();
        } else {
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

    public Result updateArticle(int id, Request request) {
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


        if (updatedArticle.isEmpty()) {
            return badRequest();
        } else {
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
        } else {
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
    public Result filterArticles(Request request) {
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

    public Result tryImage(Request request) throws IOException {

        Http.MultipartFormData body = request.body().asMultipartFormData();


        //import play.libs.Files.TemporaryFile;
        Http.MultipartFormData.FilePart<TemporaryFile> picture = body.getFile("images");



        createArticleWithMulipartData(body);

        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            System.out.println("fileName:" + fileName);
            TemporaryFile file = picture.getRef();
            file.copyTo(Paths.get("public/images/Bild4.jpg"), true);

            return ok("File uploaded");
        } else {
            return ok("this is not support now");
        }
        }

    public Result saveImageinList() throws IOException {
        BufferedImage imgfromFile= ImageIO.read(new File("C:\\Users\\helen\\Documents\\htwg6\\Teamprojekt\\dabo\\backend\\target\\images\\Bild.jpg"));

        ArrayList<BufferedImage> arrayList = new ArrayList<>();
        arrayList.add(imgfromFile);

        BufferedImage img = arrayList.get(0);

        ImageInputStream is = ImageIO.createImageInputStream(img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());


    return ok(bais).as("image/jpeg");

    }




    private Result createArticleWithMulipartData(Http.MultipartFormData body) throws IOException {

        Map<String, String[]> data = body.asFormUrlEncoded() ;

        System.out.println(data.entrySet());

        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            System.out.println(new String(entry.getKey().getBytes("UTF-8"),"ASCII"));
            System.out.println(new String(entry.getValue()[0].getBytes("UTF-8"),"ASCII"));
            System.out.println(data.size());

        }
        return ok();

    }



}