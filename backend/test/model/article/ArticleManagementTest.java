package model.article;


import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Sextet;
import org.junit.Test;
import org.mockito.Mockito;
import play.test.WithApplication;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ArticleManagementTest extends WithApplication {

    @Test
    public void showArticleTest() throws IOException {
        Sextet result =  new Sextet<>(0, "blabla", "nana", "kaka", "zack zack", new ArrayList<>());

        ArticleManagement articleManagement = Mockito.mock(ArticleManagement.class);
        Mockito.when(articleManagement.getArticleById(0)).thenReturn(result);


       Sextet testresult = articleManagement.getArticleById(0);


       System.out.println(testresult.toList());
        assertEquals(testresult,result);

    }
//    @Test
//    public void testDeleteArticel() {
//        ArticleManagement testArticel = new ArticleManagement();
//
//        testArticel.createArticle("Bierpong-tisch", "only used twice",
//                "20.02.1996", "Berlin");
//        assertTrue(testArticel.deleteArticle(0));
//
//    }

//    @Test
//    public void testCreateANewArticle() {
//        ArticleManagement testArticel = new ArticleManagement();
//
//      int artikleTestID =   testArticel.createArticle("Bierpong-tisch","only used twice",
//                "20.02.1996", "Berlin");
//        assertEquals("Bierpong-tisch",testArticel.articleMap.get(0).get(0));
//        assertEquals("only used twice",testArticel.articleMap.get(0).get(1));
//        assertEquals("20.02.1996",testArticel.articleMap.get(0).get(2));
//        assertEquals("Berlin",testArticel.articleMap.get(0).get(3));
//
//        assertEquals(artikleTestID,0);
//
//    }
//

//
//    @Test
//    public void updateArticleTest() throws IOException {
//
//        ArticleManagement testArticel = new ArticleManagement();
//
//        int artikleTestID =   testArticel.createArticle("\"Bierpong-tisch\"","\"only used twice\"",
//                "\"20.02.1996\"", "\"Berlin\"");
//
//        JsonNode json =   testArticel.updateArticle(artikleTestID,"\"Bierpong-tisch\"","\"Never used\"",
//                "\"20.02.1996\"", "\"Berlin\"");
//
//        String description = json.get("description").toString();
//
//        assertEquals("\"Never used\"",description);
//
//    }


}
