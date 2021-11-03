package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    CLOSEDDOOR("closedDoor"),
    OPENDOOR("openDoor"),
    STAIRSDOWN("stairsDown"),
    TREE("tree"),
    BRIDGE("bridgeTile"),
    BOULDER("boulder"),
//    BROKENBRIDGE("brokenBridgeTile"),
    WATER("water"),
    WALL("wall");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

}
