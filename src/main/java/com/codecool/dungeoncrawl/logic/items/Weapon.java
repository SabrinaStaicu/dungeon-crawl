package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, int damage) {
        this.damage = damage;
        this.displayName = name;
    }

    public Weapon(Cell cell, String name, int damage, String tileName) {
        this.damage = damage;
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

    public int getDamage() {
        return damage;
    }
}
