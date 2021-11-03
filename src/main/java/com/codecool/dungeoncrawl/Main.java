package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Chicken;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.sql.SQLException;

public class Main extends Application {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);


    GameMap map = MapLoader.loadMap("/map.txt");
    GameMap map2 = MapLoader.loadMap("/mapFloorTwo.txt");
    GameMap mapOneCopy = map;
    GameMap mapTwoCopy = map2;


    Canvas canvas = new Canvas(
            7 * Tiles.TILE_WIDTH,
            7 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();

    public Button pickUpItemButton = new Button("Pick up") {public void requestFocus(){}};

    ListView<String> viewInventoryItems = new ListView<>() {public void requestFocus(){}};

    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

//        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 0, 0);
        ui.add(attackLabel, 0, 1);

//        pickUpItemButton.setVisible(true);
//        pickUpItemButton.setOnAction(actionEvent -> {
//            System.out.println("pressed");
//        });

//        ui.add(pickUpItemButton, 0, 1);
        ui.add(new Label("Inventory: "), 0, 2);

        viewInventoryItems.setPrefWidth(180);
        ui.add(viewInventoryItems, 0, 3);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);


        AnimationTimer timer = new MyTimer();
        timer.start();


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case S:
                Player player = map.getPlayer();
                dbManager.savePlayer(player);
                break;
        }
    }


    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        ArrayList<Actor> mobs = new ArrayList<>();

        for (int xx = 0; xx < map.getWidth(); xx++) {
            for (int yy = 0; yy < map.getHeight(); yy++) {
                Cell cell = map.getCell(xx, yy);

                if ((cell.getActor() != null && cell.getActor().getHealth() >= 1) &&
                        (cell.getActor() instanceof Skeleton || cell.getActor() instanceof Chicken)) {
//                    cell.getActor().moveToRandomNextCell();
                    mobs.add(cell.getActor());
                }
            }
        }

        for (Actor actor : mobs) {
            actor.moveToRandomNextCell();
        }

        Player player = map.getPlayer();
        int centerX = player.getX();
        int centerY = player.getY();
        int startX = centerX - 3;
        int startY = centerY - 3;
        int endX = centerX + 3;
        int endY = centerY + 3;


        for (int x = startX, screenX = 0; x < map.getWidth() && x < endX+2; x++, screenX++) {
            for (int y = startY, screenY =0; y < map.getHeight() && y < endY+2; y++, screenY++) {
                if (x < 0) {
                    x = 0;
                }
                if (y < 0) {
                    y = 0;
                }
                Cell cell = map.getCell(x, y);
                if (cell.getActor() instanceof Player && cell.getType() == CellType.OPENDOOR) {
//                    Player player = map.getPlayer();
                    cell.setActor(null);
                    this.mapOneCopy = map;
                    this.map = this.map2;
                    refresh();
                } else if (cell.getActor() instanceof Player && cell.getType() == CellType.STAIRSDOWN) {
                    cell.setActor(null);
                    this.mapTwoCopy = map;
                    this.map = this.mapOneCopy;
//                    cell.setType(CellType.FLOOR);
                    refresh();
                }


                if (cell.getActor() != null && cell.getActor().getHealth() >= 1) {
                    Tiles.drawTile(context, cell.getActor(), screenX, screenY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), screenX, screenY);
                } else if (cell.getType() == CellType.CLOSEDDOOR) {
                    for (Item item : Inventory.getPlayerInventory()) {
                        if (item.getTileName().equals("floorTwoKey")) {
                            cell.setType(CellType.OPENDOOR);
                        }
                    }
                    Tiles.drawTile(context, cell, screenX, screenY);
                } else {
                    Tiles.drawTile(context, cell, screenX, screenY);
                }

            }
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        attackLabel.setText("Attack: " + map.getPlayer().getAttack());


        viewInventoryItems.getItems().clear();
        for (Item item : Inventory.getPlayerInventory()) {
            if (item != null) {
                viewInventoryItems.getItems().add(item.getDisplayName());
            }
        }
        viewInventoryItems.getItems();

    }

    private class MyTimer extends AnimationTimer {

        private long lastUpdate = 0;
        @Override
        public void handle(long now) {
//            Platform.runLater(() -> {
//            Runnable mobsRefresh = Main.this::refresh;
//            ses.schedule(mobsRefresh, 5, TimeUnit.SECONDS);
//        });

            if (now - lastUpdate >= 400_000_000) {
                lastUpdate = now;
                refresh();
            }
        }
    }


    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
