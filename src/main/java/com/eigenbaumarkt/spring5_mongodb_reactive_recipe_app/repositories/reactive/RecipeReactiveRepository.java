package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {


}
