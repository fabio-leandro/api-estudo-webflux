package com.fabio.apiheroes.controllers;

import com.fabio.apiheroes.entities.Hero;
import com.fabio.apiheroes.exceptions.HeroNotFoundException;
import com.fabio.apiheroes.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/heroes")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @PostMapping
    public ResponseEntity<Mono<Hero>> save(@RequestBody Hero hero){
        return ResponseEntity.status(HttpStatus.CREATED).body(heroService.save(hero));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Hero> findAll(){
        return heroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Hero>> findById(@PathVariable String id) throws HeroNotFoundException {
        Mono<Hero> heroMono = heroService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(heroMono);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable String id){
        Mono<Void> voidMono = heroService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(voidMono);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Hero>> updateById(@RequestBody Hero hero, @PathVariable String id) throws HeroNotFoundException {
        Mono<Hero> heroMono =  heroService.updateById(hero,id);
        return ResponseEntity.status(HttpStatus.OK).body(heroMono);
    }

}
