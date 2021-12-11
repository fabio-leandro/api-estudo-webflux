package com.fabio.apiheroes.controllers;

import com.fabio.apiheroes.builders.HeroBuilder;
import com.fabio.apiheroes.entities.Hero;
import com.fabio.apiheroes.exceptions.HeroNotFoundException;
import com.fabio.apiheroes.services.HeroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HeroController.class)
public class HeroControllerTest {

    private final static String API_ENDPOINT = "/api/v1/heroes";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HeroService heroService;

    @Test
    void ShouldSaveHero(){

        //given
        Hero hero = HeroBuilder.builder().build().toHero();

        //when
        Mockito.when(heroService.save(hero)).thenReturn(Mono.just(hero));

        //then
        webTestClient.post().uri(API_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(hero), Hero.class)
                .exchange()
                .expectStatus()
                .isCreated();

    }

    @Test
    void ShouldReturnFluxByHero(){

        //given
        Hero hero = HeroBuilder.builder().build().toHero();
        List<Hero> heroes = Collections.singletonList(hero);
        Flux<Hero> heroFlux = Mono.just(heroes).flatMapMany(h -> Flux.fromIterable(h));

        //when
        Mockito.when(heroService.findAll()).thenReturn(heroFlux);

        //When
        webTestClient.get().uri(API_ENDPOINT)
                .exchange()
                .expectStatus()
                .isOk();


    }

    @Test
    void ShouldReturnMonoHeroWhenIdInformed() throws HeroNotFoundException {

        //given
        Hero hero = HeroBuilder.builder().build().toHero();

        //when
        Mockito.when(heroService.findById(hero.getId())).thenReturn(Mono.just(hero));

        //then
        webTestClient.get().uri(API_ENDPOINT+"/"+hero.getId())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void ShouldReturnStatusOkWhenCalledDeleteById(){
         //given
        Hero hero = HeroBuilder.builder().build().toHero();

        //when
        Mockito.when(heroService.deleteById(hero.getId())).thenReturn(Mono.empty());

        //then
        webTestClient.delete().uri(API_ENDPOINT+"/"+hero.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void ShouldReturnStatusOkForUpdateById() throws HeroNotFoundException {

        //given
        Hero hero = HeroBuilder.builder().build().toHero();

        //when
        Mockito.when(heroService.updateById(hero,hero.getId())).thenReturn(Mono.just(hero));

        //then
        webTestClient.put().uri(API_ENDPOINT+"/"+hero.getId())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(hero), Hero.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

}
