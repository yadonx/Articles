package com.example.articletest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:08
 * Project: Articles
 * Package: com.example.articletest.repository
 */

@DataMongoTest
public class ArticleRepositoryTest  {

    @Autowired
    ArticleRepository repository;



}


