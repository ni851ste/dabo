package model.article;

import org.junit.Test;

import static org.junit.Assert.*;


public class ArticleTest {

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
    public void testDeleteArticel(){
        Article testArticel = new Article();

       testArticel.addArticle("Bierpong-tisch","only used twice",
                "20.02.1996" ,"Berlin");
        assertTrue(testArticel.deleteArticle(0));

    }

}
