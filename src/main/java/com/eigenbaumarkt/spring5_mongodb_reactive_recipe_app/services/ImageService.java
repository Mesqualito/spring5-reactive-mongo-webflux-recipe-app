package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Void> saveImageFile(String recipeId, MultipartFile file);

}
