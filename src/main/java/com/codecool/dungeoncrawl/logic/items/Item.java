package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    protected Cell cell;
    protected String displayName;
    protected String tileName;

    public abstract void spawnItem();

    public abstract void pickUpItem();

    public String getDisplayName() {
        return this.displayName;
    }


}
