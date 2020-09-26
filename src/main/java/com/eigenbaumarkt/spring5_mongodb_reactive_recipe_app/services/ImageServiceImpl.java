package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Recipe;
import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl( RecipeReactiveRepository recipeReactiveRepository) {

        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] imageByteObjects = new Byte[0];
                    try {
                        imageByteObjects = new Byte[file.getBytes().length];
                        int i = 0;
                        for(byte b : file.getBytes()) {
                            imageByteObjects[i++] = b;
                        }
                        recipe.setImage(imageByteObjects);
                        return recipe;
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        throw new RuntimeException(ioe);
                    }
                });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
