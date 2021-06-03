package com.example.articletest.services;

import com.example.articletest.models.Article;
import com.example.articletest.repository.ArticleRepository;
import com.example.articletest.services.exception.ArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:18
 * Project: Articles
 * Package: com.example.articletest.services
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    public Article createArticle (Article article){

        if (article.getAuthor() == null || article.getAuthor().isBlank())
            throw new ArticleException("Author is null");

        return repository.save(article);
    }

    public Article readArticle(String id){
        Optional<Article> temp = repository.findById(id);
        if (temp.isEmpty()){
            throw new ArticleException("Article with that id doesn't exist");
        }
        return temp.get();
    }

    public Article updateArticle(Article article){
        Optional<Article> temp = repository.findById(article.getId());

        if (temp.isEmpty())
            throw new ArticleException("Article with that id doesn't exist");

        repository.save(article);
        return article;
    }

    public String deleteArticle(String id){
        Optional<Article> temp = repository.findById(id);
        if (temp.isEmpty()){
            throw new ArticleException("Article with that id doesn't exist");
        }
        repository.delete(temp.get());
        return id + " was deleted";
    }

    public Iterable<Article> getAllArticle(){
        return repository.findAll();
    }

    public Iterable<Article> getArticlesByAuthor(String author){
        List<Article> articles = (List<Article>) repository.findByAuthor(author);
        if (articles.isEmpty())
            throw new ArticleException("No author by that name");

        return articles;
    }

}
