package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;


public class ArticleManagementHttpAdapterTest extends WithApplication {

    @Test
    public void testcreateArticleJson() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File from = new File("test\\resources\\createArticle.json");
        JsonNode createArticleJson = mapper.readTree(from);

        Http.RequestBuilder requestshow =new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles/create")
                .bodyJson(createArticleJson);

        Result result = route(app,requestshow);

        assertEquals(OK, result.status());


        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test\\target\\createArtikleTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File actuallyFile = new File("test\\resources\\getArticle.json");

        JsonNode testJson = mapper.readTree(testFile);
        JsonNode actuallyJson = mapper.readTree(actuallyFile);

        assertEquals(actuallyJson,testJson);

    }
//
//    @Test
//    public void testRequestDeleteArtikel() throws IOException {
//
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(POST)
//                .uri("/users/articles/delete/0");
//
//        Result result = route(app,request);
//        assertEquals(OK, result.status());
//    }

//    @Test
//    public void testRequestShowArtikel() throws IOException {
//
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(GET)
//                .uri("/users/articles/0");
//
//        System.out.println("show" + request.body().toString());
//
//
//
//    }


}
