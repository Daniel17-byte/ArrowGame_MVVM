package org.danielsa.proiect_ps.models;

import lombok.Getter;

@Getter
public class Move {
    private final int x;
    private final int y;
    private final Arrow arrow;

    public Move(int x, int y, Arrow arrow) {
        this.x = x;
        this.y = y;
        this.arrow = arrow;
    }

}
