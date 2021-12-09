package com.fabio.apiheroes.services;

import com.fabio.apiheroes.builders.HeroBuilder;
import com.fabio.apiheroes.entities.Hero;
import com.fabio.apiheroes.repositories.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
public class HeroServiceTeste {

    @InjectMocks
    private HeroServiceImpl heroService;

    @Mock
    private HeroRepository heroRepository;

    @Test
    void ShouldCreateHero(){
        //givenn
        Hero hero = HeroBuilder.builder().build().toHero();

        //when
        Mockito.when(heroRepository.save(hero)).thenReturn(Mono.just(hero));

        //then
        Mono<Hero> save = heroService.save(hero);

        StepVerifier.create(save)
                .consumeNextWith(newHero -> Assertions.assertEquals(hero, newHero))
                .verifyComplete();
    }

    @Test
    void ShouldReturnAFluxByHeros(){

        //given
        Hero hero = HeroBuilder.builder().build().toHero();
        List<Hero> heroes = Collections.singletonList(hero);
        Flux<Hero> heroFlux = Mono.just(heroes).flatMapMany(h -> Flux.fromIterable(h));

        //then
        Mockito.when(heroRepository.findAll())
                .thenReturn(heroFlux);

        //then
        Flux<Hero> fluxHero = heroService.findAll();

        StepVerifier.create(fluxHero)
                .consumeNextWith(fh ->{
                    Assertions.assertEquals(heroes.get(0).getId(),fh.getId());
                    Assertions.assertEquals(heroes.get(0).getName(),fh.getName());
                    Assertions.assertEquals(heroes.get(0).getUniverse(),fh.getUniverse());
                    Assertions.assertEquals(heroes.get(0).getFilms(),fh.getFilms());
                }).verifyComplete();
    }





}
