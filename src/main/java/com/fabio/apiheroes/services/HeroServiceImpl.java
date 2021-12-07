package com.fabio.apiheroes.services;

import com.fabio.apiheroes.entities.Hero;
import com.fabio.apiheroes.exceptions.HeroNotFoundException;
import com.fabio.apiheroes.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroServiceImpl implements HeroService{

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public Mono<Hero> save(Hero hero) {
        return heroRepository.save(hero);
    }

    @Override
    public Flux<Hero> findAll() {
        return heroRepository.findAll();
    }

    @Override
    public Mono<Hero> findById(String id) throws HeroNotFoundException {
        Mono<Hero> monoHero = heroRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new HeroNotFoundException(id)));
        return monoHero;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        Mono<Void> voidMono = heroRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new HeroNotFoundException(id))).then(heroRepository.deleteById(id));
        return voidMono;
    }

    @Override
    public Mono<Hero> updateById(Hero hero, String id) throws HeroNotFoundException {
       Mono<Hero> monoHero = heroRepository.findById(id)
               .switchIfEmpty(Mono.error(() -> new HeroNotFoundException(id))).then(heroRepository.save(hero));
        return monoHero;
    }
}
