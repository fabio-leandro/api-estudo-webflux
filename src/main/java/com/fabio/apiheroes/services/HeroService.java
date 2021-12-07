package com.fabio.apiheroes.services;

import com.fabio.apiheroes.entities.Hero;
import com.fabio.apiheroes.exceptions.HeroNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroService {

    Mono<Hero> save(Hero hero);
    Flux<Hero> findAll();
    Mono<Hero> findById(String id) throws HeroNotFoundException;
    Mono<Void> deleteById(String id);
    Mono<Hero> updateById(Hero hero, String id) throws HeroNotFoundException;
}
