package model.article;

import org.checkerframework.checker.nullness.Opt;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import persistence.ArticleMapAdapter;
import persistence.IArticlePersistenceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ArticleManagementTest {

    @Mock
    IArticlePersistenceAdapter database = new ArticleMapAdapter();

    @InjectMocks
    ArticleManagement articleManagement = new ArticleManagement();


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createArticleTest(){
        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet shouldBemockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today","Berlin", al);
        Quintet mockCreateArticle = new Quintet<>("Hecken Schere", "scharfe Heckenschere", "today", "Berlin", al);

        Mockito.when(database.createArticle(0,mockCreateArticle)).thenReturn(Optional.of(shouldBemockArticle));

        Optional<Sextet<Integer, String, String, String, String, List<String>>> result = articleManagement.createArticle(mockCreateArticle);

        assertEquals(Optional.of(shouldBemockArticle), result);
    }

    @Test
    public void getArticleByIdTest(){
        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet shouldBemockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today","Berlin", al);


        Mockito.when(database.getArticleById(0)).thenReturn(Optional.of(shouldBemockArticle));


        Optional<Sextet<Integer, String, String, String, String, List<String>>> result = articleManagement.getArticleById(0);

        assertEquals(Optional.of(shouldBemockArticle),result);
    }

    @Test
    public void updateArticleTest(){
        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Quintet mockToBeUpdatedArticle = new Quintet<>("Hecken Schere", "super scharfe Heckenschere","today","Konstanz", al);
        Sextet shouldBemockUpdateArticle =  new Sextet<>(0, "Hecken Schere", "super scharfe Heckenschere", "today", "Konstanz",al);

        Mockito.when(database.updateArticle(0,mockToBeUpdatedArticle)).thenReturn(Optional.of(shouldBemockUpdateArticle));

        Optional<Sextet<Integer, String, String, String, String, List<String>>> result = articleManagement.updateArticle(0,mockToBeUpdatedArticle);

        assertEquals(Optional.of(shouldBemockUpdateArticle),result);
    }

    @Test
    public void deleteArticleTest(){
        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet shouldBemockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today","Berlin", al);

        Mockito.when(database.deleteArticle(0)).thenReturn(Optional.of(shouldBemockArticle));

        Optional<Sextet<Integer, String, String, String, String, List<String>>> result = articleManagement.deleteArticle(0);

        assertEquals(Optional.of(shouldBemockArticle),result);
    }

    @Test
    public void filterArticlesTest(){
        ArrayList<String> al =  new ArrayList<>();
        al.add("Tools");
        Sextet shouldBemockArticle =  new Sextet<>(0, "Hecken Schere", "scharfe Heckenschere", "today","Berlin", al);


        Sextet mockArticleWithID1 =  new Sextet<>(1, "Hecken Schere", "Heckenschere", "today","Konstanz", al);
        Sextet mockArticleWithID2 =  new Sextet<>(2, "Tisch", "Heckenschere", "today","Berlin", al);

        List<Sextet<Integer, String, String, String, String, List<String>>> shouldBefilterList = new ArrayList<>();
        shouldBefilterList.add(shouldBemockArticle);
        shouldBefilterList.add(mockArticleWithID1);
        shouldBefilterList.add(mockArticleWithID2);

        Mockito.when(database.filterArticles(al)).thenReturn(shouldBefilterList);

        List<Sextet<Integer, String, String, String, String, List<String>>> result = articleManagement.filterArticles(al);

        assertEquals(shouldBefilterList,result);
    }
}
