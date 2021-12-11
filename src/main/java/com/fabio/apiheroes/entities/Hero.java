package com.fabio.apiheroes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tb_heroes")
public class Hero {

    @Id
    private String id;
    private String name;
    private String universe;
    private int films;

}
