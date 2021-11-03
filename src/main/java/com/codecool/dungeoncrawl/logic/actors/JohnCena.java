package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class JohnCena extends Actor {

    public JohnCena(Cell cell) {
        super(cell);
        this.setAttack();
        this.setHealth(100);
    }

    @Override
    public String getTileName() {
        return "johncena";
    }

    @Override
    public void setAttack() {
        this.attack = 100;
    }
}
