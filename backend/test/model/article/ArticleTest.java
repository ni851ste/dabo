package model.article;

import model.user.User;
import org.junit.Test;

import static org.junit.Assert.*;


public class ArticleTest {

    @Test
    public void testCreateANewArticle() {
        Article test = new Article();

        assertTrue(test.createArticle("Bierpong-tisch","only used twice",
                "20.02.1996", "18.04.1993","5 Stars",
                "Berlin", "Sports & Leisure"));
        assertEquals("Bierpong-tisch",test.article.get(0).get(0));
        assertEquals("only used twice",test.article.get(0).get(1));
        assertEquals("20.02.1996",test.article.get(0).get(2));
        assertEquals("18.04.1993",test.article.get(0).get(3));
        assertEquals("5 Stars",test.article.get(0).get(4));
        assertEquals("Berlin",test.article.get(0).get(5));
        assertEquals("Sports & Leisure",test.article.get(0).get(6));

    }

}
