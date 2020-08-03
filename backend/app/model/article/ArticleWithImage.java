package model.article;

import org.javatuples.Octet;
import org.json.JSONArray;
import org.json.JSONObject;
import play.libs.Files;
import play.mvc.Http;
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
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static play.mvc.Results.ok;

public class ArticleWithImage {

    public Result createArticleWithImage(Http.Request request) throws IOException {

        Http.MultipartFormData body = request.body().asMultipartFormData();

        //import play.libs.Files.TemporaryFile;
        Http.MultipartFormData.FilePart<Files.TemporaryFile> picture = body.getFile("images");

        createArticleWithMulipartData(body);

        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            System.out.println("fileName:" + fileName);
            Files.TemporaryFile file = picture.getRef();
            file.copyTo(Paths.get("target\\images\\Bild1.jpg"), true);

            return ok("File uploaded");
        } else {
            return ok("this is not support now");
        }
    }

    public Result getImage() throws IOException {
        BufferedImage imgfromFile= ImageIO.read(new File("target\\images\\Bild.jpg"));

        ArrayList<BufferedImage> arrayList = new ArrayList<>();
        arrayList.add(imgfromFile);

        BufferedImage img = arrayList.get(0);

        ImageInputStream is = ImageIO.createImageInputStream(img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        return ok(bais).as("image/jpeg");

    }

    public Result getArticleWithImage () throws IOException {

        BufferedImage img= ImageIO.read(new File("target\\images\\Bild.jpg"));

        ImageInputStream is = ImageIO.createImageInputStream(img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        ArrayList<ByteArrayInputStream> arrayList = new ArrayList<>();
        arrayList.add(bais);

        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");

        Octet<Integer, String, String, String, String, String, List<ByteArrayInputStream>, List<String>> article = testArticle();

        JSONArray foundArticles = new JSONArray();

        JSONObject foundArticleJson = new JSONObject();

        foundArticleJson.put("id", article.getValue0())
                .put("name", article.getValue1())
                .put("description", article.getValue2())
                .put("insertionDate", article.getValue3())
                .put("location", article.getValue4())
                .put("userId", article.getValue5());

        JSONObject images = new JSONObject();

        byte[] arrayImage = new byte[article.getValue6().get(0).available()];
        article.getValue6().get(0).read(arrayImage);

        String base64Image = Base64.getEncoder().encodeToString(arrayImage);
        images.put("images",base64Image);

        foundArticles.put(foundArticleJson)
                .put(images);

        return ok(foundArticles.toString()).as("application/json");
    }


    private  Octet<Integer, String, String, String, String, String, List<ByteArrayInputStream>, List<String>> testArticle() throws IOException {
        BufferedImage img= ImageIO.read(new File("target\\images\\Bild.jpg"));

        ImageInputStream is = ImageIO.createImageInputStream(img);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        ArrayList<ByteArrayInputStream> arrayList = new ArrayList<>();
        arrayList.add(bais);

        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");


        Octet testOctet =  new Octet<>(0, "Nili", "Nili und ihre Tochter", "today","Berlin","UikdaikenID528",arrayList, al);


        return testOctet;
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
