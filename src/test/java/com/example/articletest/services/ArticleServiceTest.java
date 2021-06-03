package com.example.articletest.services;

import com.example.articletest.models.Article;
import com.example.articletest.repository.ArticleRepository;
import com.example.articletest.services.exception.ArticleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:18
 * Project: Articles
 * Package: com.example.articletest.services
 */

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository repository;

    @InjectMocks
    private ArticleService service;


    @Test
    public void test_createArticle(){
        Article expected = new Article("1","test Title","test text", "test desc","test Author");

        when(repository.save(expected)).thenReturn(expected);

        Article article = new Article("1","test Title","test text", "test desc","test Author");
        Article actually = service.createArticle(article);

        assertEquals(expected.getAuthor(), actually.getAuthor());
        assertEquals(expected.getText(), actually.getText());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void test_createArticle_when_author_null(){
        Article expected = new Article("1","test Title","test text", "test desc",null);

        assertThrows(ArticleException.class, () -> service.createArticle(expected));

        verify(repository, times(0)).save(any());
    }

    @Test
    public void test_createArticle_when_author_empty_String(){
        Article expected = new Article("1","test Title","test text", "test desc","");

        assertThrows(ArticleException.class, () -> service.createArticle(expected));

        verify(repository, times(0)).save(any());
    }

    @Test
    public void test_readArticle(){
        Article expected = new Article("1","test Title","test text", "test desc","test Author");
        when(repository.findById("1")).thenReturn(java.util.Optional.of(expected));

        Article actually = service.readArticle("1");

        assertEquals(expected.getText(), actually.getText());
        assertEquals(expected.getAuthor(),actually.getAuthor());

        verify(repository,times(1)).findById("1");
    }

    @Test
    public void test_readArticle_wrong_id(){
        assertThrows(ArticleException.class, () -> service.readArticle("1"));

        verify(repository,times(1)).findById("1");
    }

    @Test
    public void test_updateArticle(){
        Article expected = new Article("1","test Title","test text", "test desc","test Author");

        when(repository.findById("1")).thenReturn(java.util.Optional.of(expected));

        Article actually = service.updateArticle(new Article("1","t","t","t","t"));

        assertEquals("t", actually.getAuthor());

        verify(repository, times(1)).findById("1");
        verify(repository,times(1)).save(any());
    }

    @Test
    public void test_updateArticle_wrong_id(){
        assertThrows(ArticleException.class, () -> service.readArticle("1"));

        verify(repository,times(1)).findById("1");
    }

    @Test
    public void test_deleteArticle(){
        Article expected = new Article("1","test Title","test text", "test desc","test Author");

        when(repository.findById("1")).thenReturn(java.util.Optional.of(expected));

        String actually = service.deleteArticle("1");

        assertEquals("1 was deleted", actually);


        verify(repository,times(1)).delete(any());
        verify(repository,times(1)).findById("1");
    }

    @Test
    public void test_deleteArticle_wrong_id(){
        assertThrows(ArticleException.class, () -> service.readArticle("1"));

        verify(repository,times(1)).findById("1");
    }


    @Test
    public void test_getAllArticle(){
        Article article1 = new Article("1","test Title1","test text1", "test desc1","test Author1");
        Article article2 = new Article("2","test Title2","test text2", "test desc2","test Author2");

        List<Article> expected = Arrays.asList(article1,article2);

        when(repository.findAll()).thenReturn(expected);

        List<Article> actually = (List<Article>) service.getAllArticle();

        assertEquals(expected.size(), actually.size());
        assertEquals(expected.get(0), actually.get(0));

        verify(repository, times(1)).findAll();
    }


    @Test
    public void test_getArticlesByAuthor(){
        Article article1 = new Article("1","test Title1","test text1", "test desc1","test Author1");
        Article article2 = new Article("2","test Title2","test text2", "test desc2","test Author1");

        List<Article> expected = Arrays.asList(article1,article2);

        when(repository.findByAuthor("test Author1")).thenReturn(expected);

        List<Article> actually = (List<Article>) service.getArticlesByAuthor("test Author1");

        assertEquals(expected.size(), actually.size());
        assertEquals(expected.get(0), actually.get(0));

        verify(repository, times(1)).findByAuthor("test Author1");
    }

    @Test
    public void test_getArticlesByAuthor_wrong_author(){

        assertThrows(ArticleException.class, () -> service.getArticlesByAuthor("test Author1"));

        verify(repository, times(1)).findByAuthor("test Author1");
    }


}