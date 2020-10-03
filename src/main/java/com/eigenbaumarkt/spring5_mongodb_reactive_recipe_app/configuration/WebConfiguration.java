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
    public RouterFunction<?> routes(RecipeService recipeService){
        return RouterFunctions.route(GET("/api/recipes"),
                serverRequest -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(recipeService.getRecipes(), Recipe.class));

    }
}
