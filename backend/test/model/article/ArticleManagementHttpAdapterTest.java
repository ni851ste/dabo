package model.article;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ArticleManagementHttpAdapterTest extends WithApplication {

//    @Test
//    public void testRequestAddArtikel() throws IOException {
//
//        String input = "{" +
//                "\"name\": \"BierPong3\"," +
//                "\"description\": \"guter Zustand\", " +
//                "\"image\": \"image\"," +
//                "\"location\": \"Berlin\"," +
//                "\"insertionDate\": \"20.02.13\"" +
//                "}";
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonFactory factory = mapper.getFactory();
//        JsonParser jp = factory.createParser(input);
//        JsonNode jsonNode = mapper.readTree(jp);
//
//        //System.out.println(jsonNode.toString());
//
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(POST)
//                .uri("/users/articles/create")
//                .bodyJson(jsonNode);
//
//        Result result = route(app,request);
//        assertEquals(OK, result.status());
//    }
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
//        String input = "{" +
//                "\"name\": \"BierPong3\"," +
//                "\"description\": \"guter Zustand\", " +
//                "\"image\": \"image\"," +
//                "\"location\": \"Berlin\"," +
//                "\"insertionDate\": \"20.02.13\"" +
//                "}";
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonFactory factory = mapper.getFactory();
//        JsonParser jp = factory.createParser(input);
//        JsonNode jsonNode = mapper.readTree(jp);
//
//
//        Http.RequestBuilder requestshow =new Http.RequestBuilder()
//                .method(POST)
//                .uri("/users/articles/create")
//                .bodyJson(jsonNode);
//
//        System.out.println("show" + requestshow.body().toString());
//
//        Http.RequestBuilder request = new Http.RequestBuilder()
//                .method(GET)
//                .uri("/users/articles/0");
//
//        System.out.println("show" + request.body().toString());
//
//
//        Result result = route(app,request);
//        assertEquals(OK, result.status());
//    }
}
