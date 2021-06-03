package com.example.articletest.repository;

import com.example.articletest.models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:08
 * Project: Articles
 * Package: com.example.articletest.repository
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    Iterable<Article> findByAuthor (String author);

}
