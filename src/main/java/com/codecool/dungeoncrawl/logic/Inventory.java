package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Inventory {

    static ArrayList<Item> playerInventory = new ArrayList<>();;

    public static void addItem(Item item) {
        playerInventory.add(item);
    }

    public static ArrayList<Item> getPlayerInventory() {
        return playerInventory;
    }

    public static void addItem() {
    }
}
