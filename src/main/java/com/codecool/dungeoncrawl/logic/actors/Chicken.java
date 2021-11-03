package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chicken extends Actor {

    public Chicken(Cell cell) {
        super(cell);
        this.setAttack();
        this.setHealth(1);
    }

    @Override
    public String getTileName() {
        return "chicken";
    }

    @Override
    public void setAttack() {
        this.attack = 1;
    }
}
