package org.danielsa.proiect_ps.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPlayer {
    private String color;
    private User user;
    public UserPlayer(String color) {
        this.color = color;
    }
}
