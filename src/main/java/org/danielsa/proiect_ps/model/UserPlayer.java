package org.danielsa.proiect_ps.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPlayer {
    private String color;
    private UserModel user;
    public UserPlayer(String color) {
        this.color = color;
    }
}
