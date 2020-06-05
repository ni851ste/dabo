package model.article;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.test.WithApplication;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ArticleTest extends WithApplication {

    @Test
    public void testCreateANewArticle() {
        Article testArticel = new Article();

      int artikleTestID =   testArticel.addArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "Berlin");
        assertEquals("Bierpong-tisch",testArticel.articleMap.get(0).get(0));
        assertEquals("only used twice",testArticel.articleMap.get(0).get(1));
        assertEquals("20.02.1996",testArticel.articleMap.get(0).get(2));
        assertEquals("Berlin",testArticel.articleMap.get(0).get(3));

        assertEquals(artikleTestID,0);

    }

    @Test
    public void testDeleteArticel() {
        Article testArticel = new Article();

        testArticel.addArticle("Bierpong-tisch", "only used twice",
                "20.02.1996", "Berlin");
        assertTrue(testArticel.deleteArticle(0));

    }

    @Test
    public void showArticleTest() throws IOException {
        Article testArticel = new Article();


      int artikleTestID =   testArticel.addArticle("\"Bierpong-tisch\"","\"only used twice\"",
                "\"20.02.1996\"", "\"Berlin\"");

     JsonNode json = testArticel.showArticle(artikleTestID);
        String name  = json.get("name").toString();
        String description = json.get("description").toString();
        String location = json.get("location").toString();
        String insertionDate = json.get("insertionDate").toString();

        assertEquals("\"Bierpong-tisch\"",name);
        assertEquals("\"only used twice\"",description);
        assertEquals("\"20.02.1996\"",insertionDate);
        assertEquals("\"Berlin\"",location);

    }

    @Test
    public void updateArticleTest() throws IOException {

        Article testArticel = new Article();

        int artikleTestID =   testArticel.addArticle("\"Bierpong-tisch\"","\"only used twice\"",
                "\"20.02.1996\"", "\"Berlin\"");

        JsonNode json =   testArticel.updateArticle(artikleTestID,"\"Bierpong-tisch\"","\"Never used\"",
                "\"20.02.1996\"", "\"Berlin\"");

        String description = json.get("description").toString();

        assertEquals("\"Never used\"",description);

    }


}
