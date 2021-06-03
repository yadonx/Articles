package com.example.articletest.controllers;

import com.example.articletest.models.Article;
import com.example.articletest.services.ArticleService;
import com.example.articletest.services.exception.ArticleException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:18
 * Project: Articles
 * Package: com.example.articletest.controllers
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/article")
public class ArticleController {

    @Autowired
    private ArticleService service;


    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestBody Article article){
        try {
            service.createArticle(article);
            return ResponseEntity.status(HttpStatus.OK).body("Article saved");
        } catch (ArticleException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<String> readArticle(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.readArticle(id).toString());
        } catch (ArticleException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateArticle(@RequestBody Article article){
        try {
            service.updateArticle(article);
            return ResponseEntity.status(HttpStatus.OK).body("Article updated");
        } catch (ArticleException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteArticle(id));
        } catch (ArticleException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Article>> getAllArticles(){
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllArticle());
    }

    @GetMapping("/get/{author}")
    public ResponseEntity<String> getByAuthor(@PathVariable String author){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getArticlesByAuthor(author).toString());
        }catch (ArticleException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }


}
