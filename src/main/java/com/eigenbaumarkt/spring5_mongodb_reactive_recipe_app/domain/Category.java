package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
@Document
public class Category {
    @Id
    private String id;
    private String description;

    // will not play well with MongoDB, see: https://jira.spring.io/browse/DATAMONGO-1584
    // @DBRef
    private Set<Recipe> recipes;
}
