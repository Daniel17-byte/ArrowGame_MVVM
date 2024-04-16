package org.danielsa.proiect_ps.model;

import lombok.Getter;

@Getter
public class MoveModel {
    private final int x;
    private final int y;
    private final ArrowModel arrowModel;

    public MoveModel(int x, int y, ArrowModel arrowModel) {
        this.x = x;
        this.y = y;
        this.arrowModel = arrowModel;
    }

}
