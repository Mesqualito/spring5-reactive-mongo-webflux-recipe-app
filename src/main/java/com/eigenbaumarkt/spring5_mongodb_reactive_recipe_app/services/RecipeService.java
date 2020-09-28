package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.commands.RecipeCommand;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand>  saveRecipeCommand(RecipeCommand command);

    Mono<Void> deleteById(String idToDelete);
}
