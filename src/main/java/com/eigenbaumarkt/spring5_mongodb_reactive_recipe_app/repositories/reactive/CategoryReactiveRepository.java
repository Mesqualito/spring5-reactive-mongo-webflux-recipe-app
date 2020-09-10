package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories.reactive;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

    // findByDescription will bring back zero or one Category => 'reactor.core.publisher.Mono'
    Mono<Category> findByDescription(String description);
}
