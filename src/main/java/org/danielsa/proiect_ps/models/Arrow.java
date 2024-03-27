package org.danielsa.proiect_ps.models;

import lombok.*;

@Getter
@EqualsAndHashCode
public class Arrow {
    @Setter
    private String color;
    private final String direction;

    public Arrow(String color, String direction) {
        this.color = color;
        this.direction = direction;
    }
    public Arrow(String direction) {
        this.direction = direction;
    }

}
