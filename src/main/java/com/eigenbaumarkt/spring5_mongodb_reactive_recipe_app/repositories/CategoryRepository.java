package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by jt on 6/13/17.
 */
public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
