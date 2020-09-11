package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {

        categoryReactiveRepository.deleteAll().block();

    }

    @Test
    public void testCategorySave() throws Exception {

        Category category = new Category();
        category.setDescription("Test-Kategorie");

        categoryReactiveRepository.save(category).block();

        assertEquals(categoryReactiveRepository.findByDescription("Test-Kategorie").block().getDescription(), "Test-Kategorie");

    }

}
