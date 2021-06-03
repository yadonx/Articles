package com.example.articletest.services.exception;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:23
 * Project: Articles
 * Package: com.example.articletest.services.exception
 */
public class ArticleException extends RuntimeException{
    public ArticleException(String errormessage) {
        super(errormessage);
    }
}
