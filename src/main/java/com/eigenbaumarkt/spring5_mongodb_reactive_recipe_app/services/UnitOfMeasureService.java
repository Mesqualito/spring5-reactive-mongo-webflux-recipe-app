package com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.services;

import com.eigenbaumarkt.spring5_mongodb_reactive_recipe_app.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

/**
 * Created by jt on 6/28/17.
 */
public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
