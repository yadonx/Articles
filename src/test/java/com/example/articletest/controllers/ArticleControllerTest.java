package com.example.articletest.controllers;

import com.example.articletest.models.Article;
import com.example.articletest.repository.ArticleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 14:53
 * Project: Articles
 * Package: com.example.articletest.controllers
 */

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ArticleRepository repository;

    @BeforeEach
    void init(){
        Article article1 = new Article("1","test Title1","test text1", "test desc1","test Author1");
        Article article2 = new Article("2","test Title2","test text2", "test desc2","test Author1");
        Article article3 = new Article("3","test Title3","test text3", "test desc3","test Author3");

        when(repository.findAll()).thenReturn(Arrays.asList(article1,article2,article3));
        when(repository.findById("1")).thenReturn(java.util.Optional.of(article1));
        when(repository.findByAuthor("test Author1")).thenReturn(Arrays.asList(article1,article2));
    }

    @Test
    void test_createArticle() throws Exception {
        Article testTask = new Article("1","test Title1","test text1", "test desc1","test Author1");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTask);

        mvc.perform(MockMvcRequestBuilders.post("/article/create").contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Article saved"));

        verify(repository, times(1)).save(any());
    }

    @Test
    void test_createArticle_author_is_null() throws Exception {
        Article testTask = new Article("1","test Title1","test text1", "test desc1",null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTask);

        mvc.perform(MockMvcRequestBuilders.post("/article/create").contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Author is null"));
        verify(repository, times(0)).save(any());
    }

    @Test
    void test_readArticle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/article/read/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Article(id=1, title=test Title1, text=test text1, description=test desc1, author=test Author1)"));
        verify(repository,times(1)).findById("1");
    }

    @Test
    void test_readArticle_wrong_id() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/article/read/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Article with that id doesn't exist"));
        verify(repository,times(1)).findById("2");
    }


    @Test
    void test_updateArticle() throws Exception {
        Article testTask = new Article("1","testing","test text1", "test desc1","test Author1");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTask);

        mvc.perform(MockMvcRequestBuilders.post("/article/update").contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Article updated"));

        verify(repository,times(1)).findById("1");
        verify(repository,times(1)).save(any());
    }

    @Test
    void test_updateArticle_wrong_id() throws Exception {
        Article testTask = new Article("4","testing","test text1", "test desc1","test Author1");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTask);

        mvc.perform(MockMvcRequestBuilders.post("/article/update").contentType(MediaType.APPLICATION_JSON).content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Article with that id doesn't exist"));

        verify(repository,times(1)).findById("4");
        verify(repository,times(0)).save(any());
    }

    @Test
    void test_deleteArticle() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/article/delete/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1 was deleted"));

        verify(repository,times(1)).findById("1");
        verify(repository,times(1)).delete(any());
    }

    @Test
    void test_deleteArticle_wrong_id() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/article/delete/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Article with that id doesn't exist"));

        verify(repository,times(1)).findById("2");
        verify(repository,times(0)).delete(any());
    }

   @Test
    void test_getAllArticles() throws Exception{
       mvc.perform(MockMvcRequestBuilders.get("/article/all").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json("[{\"id\":\"1\",\"title\":\"test Title1\",\"text\":\"test text1\",\"description\":\"test desc1\",\"author\":\"test Author1\"},{\"id\":\"2\",\"title\":\"test Title2\",\"text\":\"test text2\",\"description\":\"test desc2\",\"author\":\"test Author1\"},{\"id\":\"3\",\"title\":\"test Title3\",\"text\":\"test text3\",\"description\":\"test desc3\",\"author\":\"test Author3\"}]"));


       verify(repository,times(1)).findAll();
   }

   @Test
    void test_getByAuthor() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/article/get/test Author1").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
            .andExpect(content().string("[Article(id=1, title=test Title1, text=test text1, description=test desc1, author=test Author1), Article(id=2, title=test Title2, text=test text2, description=test desc2, author=test Author1)]"));

        verify(repository,times(1)).findByAuthor("test Author1");
   }

   @Test
    void test_getByAuthor_wrong_author() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/article/get/test Author2").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
            .andExpect(content().string("No author by that name"));

        verify(repository,times(1)).findByAuthor("test Author2");
   }
}