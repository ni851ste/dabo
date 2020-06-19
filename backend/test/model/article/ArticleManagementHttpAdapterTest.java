package model.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

import org.mockito.Mockito;
import static play.inject.Bindings.bind;
import static play.test.Helpers.*;

public class ArticleManagementHttpAdapterTest extends WithApplication {

    @Override
    public Application provideApplication() {

        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet mockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "Berlin", "today", al);
        Quintet mockToBeUpdatedArticle = new Quintet<>("Hecken Schere", "super scharfe Heckenschere", "Konstanz", "today", al);
        Sextet mockUpdateArticle =  new Sextet<>(0, "Hecken Schere", "super scharfe Heckenschere", "Konstanz", "today", al);

        Sextet mockArticleWithID1 =  new Sextet<>(1, "Hecken Schere", "Heckenschere", "Berlin", "today", al);
        Sextet mockArticleWithID2 =  new Sextet<>(2, "Tisch", "Heckenschere", "Berlin", "today", al);

        List<Sextet<Integer, String, String, String, String, List<String>>> filterList = new ArrayList<>();
        filterList.add(mockArticle);
        filterList.add(mockArticleWithID1);
        filterList.add(mockArticleWithID2);



        ArticleManagement articleManagement = Mockito.mock(ArticleManagement.class);
        Mockito.when(articleManagement.getArticleById(0)).thenReturn(mockArticle);
        Mockito.when(articleManagement.deleteArticle(0)).thenReturn(mockArticle);
        Mockito.when(articleManagement.updateArticle(0,mockToBeUpdatedArticle)).thenReturn(mockUpdateArticle);

        return new GuiceApplicationBuilder()
                .in(new File("."))
                .in(Mode.TEST)
                .configure("model.article",true)
                .bindings(bind(ArticleManagement.class).toInstance(articleManagement))
                .build();
    }

    @Test
    public void testcreateArticle() throws IOException {

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

    @Test
    public void testGetArticle() throws IOException {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/users/articles/0");

        System.out.println("show" + request.body().toString());

        Result result = route(app,request);
        String resultJsonToString = Helpers.contentAsString(result, mat);
        System.out.println("nananana"+ resultJsonToString);
        assertEquals(OK, result.status());

        ObjectMapper mapper = new ObjectMapper();

        File actuallyFile = new File("test\\resources\\getArticle.json");

        JsonNode testJson = mapper.readTree(resultJsonToString);

        JsonNode actuallyJson = mapper.readTree(actuallyFile);

        assertEquals(actuallyJson,testJson);

    }

    @Test
    public void testUpdateArticle() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File actuallyFile = new File("test\\resources\\updateArticle.json");
        JsonNode updateArticleJson = mapper.readTree(actuallyFile);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles/update/0")
                .bodyJson(updateArticleJson);

        Result result = route(app,request);

        assertEquals(OK, result.status());

        String resultJsonToString = Helpers.contentAsString(result, mat);


        File updateFile = new File("test\\resources\\updateArticle.json");

        JsonNode testJson = mapper.readTree(resultJsonToString);

        JsonNode actuallyJson = mapper.readTree(updateFile);


        assertEquals(updateArticleJson,actuallyJson);


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
        File actuallyFile = new File("test\\resources\\filterArticle.json");
        JsonNode filterArticleJson = mapper.readTree(actuallyFile);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/users/articles")
                .bodyJson(filterArticleJson);

        Result result = route(app,request);

        assertEquals(OK, result.status());

    }

}
