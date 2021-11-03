package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {

    public Key(Cell cell, String name, String tileName) {
        this.displayName = name;
        this.tileName = tileName;
        this.cell = cell;
        this.cell.setItem(this);
    }

    @Override
    public void spawnItem() {

    }

    @Override
    public void pickUpItem() {

    }

    @Override
    public String getTileName() {
        return this.tileName;
    }
}
