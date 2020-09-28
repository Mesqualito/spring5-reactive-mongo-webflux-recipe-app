package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.commands.IngredientCommand;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.commands.UnitOfMeasureCommand;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.converters.IngredientCommandToIngredient;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.converters.IngredientToIngredientCommand;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Ingredient;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Recipe;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive.RecipeReactiveRepository;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeReactiveRepository recipeReactiveRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        // with reactive streams:
        return recipeReactiveRepository
                .findById(recipeId)
                // reactor.core.publisher.Mono<T> public final <R> reactor.core.publisher.Flux<R> flatMapIterable(@NotNull java.util.function.Function<? super T, ? extends Iterable<? extends R>> mapper)
                .flatMapIterable(Recipe::getIngredients)
                // reactor.core.publisher.Flux<T> public final Flux<T> filter(@NotNull java.util.function.Predicate<? super T> p)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                // reduce it to Mono with reactor.core.publisher.Flux<T> public final reactor.core.publisher.Mono<T> single()
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {

        return recipeReactiveRepository
                .findById(command.getRecipeId())
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(command.getId()))
                .single()
                .map(ingredient -> {
                    IngredientCommand tempCommand = ingredientToIngredientCommand.convert(ingredient);
                    tempCommand.setId(command.getId());
                    tempCommand.setRecipeId(command.getRecipeId());
                    tempCommand.setDescription(command.getDescription());
                    tempCommand.setAmount(command.getAmount());
                    tempCommand.setUom(unitOfMeasureReactiveRepository
                            .findByDescription(command.getDescription())
                            .map(unitOfMeasure -> {
                                UnitOfMeasureCommand tempUomCommand = new UnitOfMeasureCommand();
                                tempUomCommand.setId(unitOfMeasure.getId());
                                tempUomCommand.setDescription(unitOfMeasure.getDescription());
                                return tempUomCommand;
                            }));
                    return tempCommand;
                });
    }

    /*

    Optional<Ingredient> ingredientOptional = recipe
            .getIngredients()
            .stream()
            .filter(ingredient -> ingredient.getId().equals(command.getId()))
            .findFirst();

            if(ingredientOptional.isPresent())

    {
        Ingredient ingredientFound = ingredientOptional.get();
        ingredientFound.setDescription(command.getDescription());
        ingredientFound.setAmount(command.getAmount());
        ingredientFound.setUom(unitOfMeasureReactiveRepository
                .findById(command.getUom().getId()).block());
        if (ingredientFound.getUom() == null) {
            new RuntimeException("No Unit of Measure objects found!");
        }
    } else

    {
        // add new Ingredient
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        recipe.addIngredient(ingredient);
    }

    Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

    Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
            .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
            .findFirst();

    // check by description
            if(!savedIngredientOptional.isPresent())

    {
        // not totally safe... But best guess
        savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                .findFirst();
    }

    // todo check for fail

    // enhance with id value
    IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
}

    }
    */

        @Override
        public Mono<Void> deleteById (String recipeId, String idToDelete){

            log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

            Mono<Recipe> recipe = recipeReactiveRepository.findById(recipeId);

            if (recipe != null) {
                log.debug("found recipe");
                Optional<Ingredient> ingredientOptional = recipe.block()
                        .getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getId().equals(idToDelete))
                        .findFirst();
                if (ingredientOptional.isPresent()) {
                    log.debug("found Ingredient");

                    recipe.block().getIngredients().remove(ingredientOptional.get());
                    recipeReactiveRepository.save(recipe.block()).block();
                }
            } else {
                log.debug("Recipe Id Not found. Id:" + recipeId);
            }
            return Mono.empty();
        }
    }
