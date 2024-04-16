package org.danielsa.proiect_ps.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPlayerModel {
    private String color;
    private UserModel user;
    public UserPlayerModel(String color) {
        this.color = color;
    }
}
