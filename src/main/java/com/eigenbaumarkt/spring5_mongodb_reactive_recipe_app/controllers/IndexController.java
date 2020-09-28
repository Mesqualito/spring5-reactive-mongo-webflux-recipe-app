package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.controllers;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        // not (yet) add in an reactive object, but the recipes as list
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
