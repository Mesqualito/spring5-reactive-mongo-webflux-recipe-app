package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.repositories;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
