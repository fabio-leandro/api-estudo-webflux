package com.fabio.apiheroes.repositories;

import com.fabio.apiheroes.entities.Hero;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends ReactiveMongoRepository<Hero,String> {
}
