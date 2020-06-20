package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.junit.Before;
import org.junit.Test;
import play.Mode;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

import org.mockito.Mockito;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

public class ArticleManagementHttpAdapterTest extends WithApplication {

    @Before
    public void setup() throws IOException
    {
        Files.createDirectories(Paths.get("test/target"));
    }

    @Override
    public Application provideApplication() {

        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet mockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today","Berlin", al);

        Quintet mockCreateArticle = new Quintet<>("Hecken Schere", "scharfe Heckenschere", "today", "Berlin", al);
        Sextet mockCreateFinishArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today", "Berlin", al);

        Quintet mockToBeUpdatedArticle = new Quintet<>("Hecken Schere", "super scharfe Heckenschere","today","Konstanz", al);
        Sextet mockUpdateArticle =  new Sextet<>(0, "Hecken Schere", "super scharfe Heckenschere", "today", "Konstanz",al);

        Sextet mockArticleWithID1 =  new Sextet<>(1, "Hecken Schere", "Heckenschere", "today","Konstanz", al);
        Sextet mockArticleWithID2 =  new Sextet<>(2, "Tisch", "Heckenschere", "today","Berlin", al);

        List<Sextet<Integer, String, String, String, String, List<String>>> filterList = new ArrayList<>();
        filterList.add(mockArticle);
        filterList.add(mockArticleWithID1);
        filterList.add(mockArticleWithID2);




        ArticleManagement articleManagement = Mockito.mock(ArticleManagement.class);
        Mockito.when(articleManagement.createArticle(mockCreateArticle)).thenReturn(mockCreateFinishArticle);
        Mockito.when(articleManagement.getArticleById(0)).thenReturn(mockArticle);
        Mockito.when(articleManagement.deleteArticle(0)).thenReturn(mockArticle);
        Mockito.when(articleManagement.updateArticle(0,mockToBeUpdatedArticle)).thenReturn(mockUpdateArticle);
        Mockito.when(articleManagement.filterArticles(al)).thenReturn(filterList);

        return new GuiceApplicationBuilder()
                .in(new File("."))
                .in(Mode.TEST)
                .configure("model.article",true)
                .bindings(bind(ArticleManagement.class).toInstance(articleManagement))
                .build();
    }

    @Test
    public void testCreateArticle() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File creatArticleFile = new File("test\\resources\\createArticle.json");
        JsonNode createArticleJson = mapper.readTree(creatArticleFile);

        Http.RequestBuilder requestshow =new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles/create")
                .bodyJson(createArticleJson);

        Result result = route(app,requestshow);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test\\target\\createArticleTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File actuallyFile = new File("test\\resources\\getArticle.json");

        JsonNode testJson = mapper.readTree(testFile);
        JsonNode actuallyJson = mapper.readTree(actuallyFile);

        assertEquals(testJson, actuallyJson);

    }

    @Test
    public void testGetArticle() throws IOException {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/users/articles/0");

        Result result = route(app,request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test\\target\\getArticleTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File actuallyFile = new File("test\\resources\\getArticle.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode testJson = mapper.readTree(resultJsonToString);
        JsonNode actuallyJson = mapper.readTree(actuallyFile);

        assertEquals(actuallyJson,testJson);

    }

    @Test
    public void testUpdateArticle() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File updateArticleFile = new File("test\\resources\\updateArticle.json");
        JsonNode updateArticleJson = mapper.readTree(updateArticleFile);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles/update/0")
                .bodyJson(updateArticleJson);

        Result result = route(app,request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test\\target\\updateArticleTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();

        File updateFile = new File("test\\resources\\createUpdateArticle.json");

        JsonNode testJson = mapper.readTree(resultJsonToString);
        JsonNode actuallyJson = mapper.readTree(updateFile);

        assertEquals(actuallyJson, testJson);

    }


    @Test
    public void testDeleteArticle() throws IOException {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles/delete/0");

        Result result = route(app,request);

        assertEquals(OK, result.status());


    }

    @Test
    public void testFilterArticles() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File filterArticleFile = new File("test\\resources\\filterArticles.json");
        JsonNode filterArticleJson = mapper.readTree(filterArticleFile);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles")
                .bodyJson(filterArticleJson);

        Result result = route(app,request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);

        File testFile = new File("test\\target\\filterArticleTest.json");
        testFile.createNewFile();
        FileWriter fileWriter = new FileWriter(testFile);

        fileWriter.write(resultJsonToString);
        fileWriter.flush();
        fileWriter.close();
        File filterListFile = new File("test\\resources\\filterListArticles.json");

        JsonNode testJson = mapper.readTree(resultJsonToString);
        JsonNode actuallyJson = mapper.readTree(filterListFile);

        assertEquals(actuallyJson,testJson);

    }

}
