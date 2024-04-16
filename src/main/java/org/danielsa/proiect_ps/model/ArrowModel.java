package org.danielsa.proiect_ps.model;

import lombok.*;

@Getter
@EqualsAndHashCode
public class ArrowModel {
    @Setter
    private String color;
    private final String direction;

    public ArrowModel(String color, String direction) {
        this.color = color;
        this.direction = direction;
    }
    public ArrowModel(String direction) {
        this.direction = direction;
    }

}
