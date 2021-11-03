package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setAttack();
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void setAttack() {
        this.attack = 2;
    }
}
