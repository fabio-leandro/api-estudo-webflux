package com.fabio.apiheroes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends Exception {

    public HeroNotFoundException(String id){
        super(String.format("Hero not found for id -> %s", id));
    }

}
