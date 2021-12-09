package com.fabio.apiheroes.builders;

import com.fabio.apiheroes.entities.Hero;
import lombok.Builder;

@Builder
public class HeroBuilder {

    @Builder.Default
    private String id = "125be";
    @Builder.Default
    private String name = "Batman";
    @Builder.Default
    private String universe = "DC";
    @Builder.Default
    private int films = 3;

    public Hero toHero(){
        return new Hero(id,name,universe,films);
    }
}
