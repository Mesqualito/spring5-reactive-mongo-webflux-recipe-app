package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.configuration;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Recipe;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebConfiguration {

    @Bean
    RouterFunction<?> routes(RecipeService recipeService) {

        // in "api/recipes"
        return RouterFunctions.route(GET("/api/recipes"),
                // we deliver a ServerResponse
                serverRequest -> ServerResponse
                        .ok()
                        // as JSON-Object
                        .contentType(MediaType.APPLICATION_JSON)
                        // get Flux with no or some Recipe-Objects
                        .body(recipeService.getRecipes(), Recipe.class));
    }

}
