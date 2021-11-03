package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.Inventory;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    protected int attack;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (this.getHealth() <= 0) {return;}
        if ((nextCell.getActor() instanceof Skeleton || nextCell.getActor() instanceof Chicken || nextCell.getActor() instanceof JohnCena) && this instanceof Player) {

            if (nextCell.getActor().getHealth() <= 0) {
                nextCell.setActor(null);
            } else {
                System.out.println("ouch");
                nextCell.getActor().takeDamage(this.getAttack());

                if (!(nextCell.getActor().getHealth() <= 0)) {
                    this.takeDamage(nextCell.getActor().getAttack());
                }
            }
        }

        if (nextCell.getType() == CellType.BOULDER && this instanceof Player) {
            Cell moveBoulder = nextCell.getNeighbor(dx, dy);
            if (moveBoulder.getType() == CellType.FLOOR) {
                nextCell.setType(CellType.FLOOR);
                moveBoulder.setType(CellType.BOULDER);
            }
        }

        if (!(nextCell.getType() == CellType.WALL || nextCell.getActor() != null || nextCell.getType() == CellType.EMPTY ||
                nextCell.getType() == CellType.CLOSEDDOOR || nextCell.getType() == CellType.WATER ||
                nextCell.getType() == CellType.TREE || nextCell.getType() == CellType.BOULDER)) {


            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            if (nextCell.getItem() != null && this instanceof Player) {

                Inventory.addItem(nextCell.getItem());
                if (nextCell.getItem() instanceof Weapon) {
                    this.addWeaponDamage(((Weapon) nextCell.getItem()).getDamage());
                }
                nextCell.setItem(null);
            }

        }
    }

    public void addWeaponDamage(int weaponDamage) {
        this.attack = this.attack + weaponDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) { this.health = health;}

    public int getAttack() {
        return this.attack;
    }

    public void takeDamage(int damageTaken) {
        this.health = this.health - damageTaken;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public abstract void setAttack();

    public void moveToRandomNextCell() {

        int randomX = ThreadLocalRandom.current().nextInt(3)-1;
        int randomY = ThreadLocalRandom.current().nextInt(3)-1;

        try {
            if (!(randomX == 0 && randomY == 0)) {
                this.move(randomX, randomY);
            }
        } catch (IndexOutOfBoundsException e) { this.moveToRandomNextCell();}
    }
}
