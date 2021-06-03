package com.example.articletest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Emil Johansson
 * Date: 2021-06-03
 * Time: 13:08
 * Project: Articles
 * Package: com.example.articletest.models
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    String id;
    String title;
    String text;
    String description;
    String author;

}
