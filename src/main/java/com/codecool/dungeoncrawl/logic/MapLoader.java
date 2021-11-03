package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Chicken;
import com.codecool.dungeoncrawl.logic.actors.JohnCena;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapPath) {
        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, "Floor 2 key", "floorTwoKey");
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon(cell, "Basic Sword", 5, "sword");
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell, "Player"));  //from dungeon crawl 2
//                            map.setPlayer(new Player(cell)); old version
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Chicken(cell);

                            break;
                        case 'j':
                            cell.setType(CellType.FLOOR);
                            new JohnCena(cell);
                            break;
                        case 'x':
                            cell.setType(CellType.CLOSEDDOOR);
                            break;
                        case 'd':
                            cell.setType(CellType.STAIRSDOWN);
                            break;
                        case 'a':
                            cell.setType(CellType.WATER);
                            break;
                        case 'b':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'o':
                            cell.setType(CellType.BOULDER);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
