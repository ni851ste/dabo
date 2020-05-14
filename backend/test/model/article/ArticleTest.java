package model.article;

import model.user.User;
import org.junit.Test;

import static org.junit.Assert.*;


public class ArticleTest {

    @Test
    public void testCreateANewArticle() {
        Article testArticel = new Article();

        assertTrue(testArticel.createArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "18.04.1993","5 Stars",
                "Berlin", "Sports & Leisure"));
        assertEquals("Bierpong-tisch",testArticel.article.get(0).get(0));
        assertEquals("only used twice",testArticel.article.get(0).get(1));
        assertEquals("20.02.1996",testArticel.article.get(0).get(2));
        assertEquals("18.04.1993",testArticel.article.get(0).get(3));
        assertEquals("5 Stars",testArticel.article.get(0).get(4));
        assertEquals("Berlin",testArticel.article.get(0).get(5));
        assertEquals("Sports & Leisure",testArticel.article.get(0).get(6));
    }

    @Test
    public void testDeleteArticel(){
        Article testArticel = new Article();

        assertTrue(testArticel.createArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "18.04.1993","5 Stars",
                "Berlin", "Sports & Leisure"));
        assertTrue(testArticel.deleteArticle(0));

    }

}
