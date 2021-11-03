package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class GameDatabaseManagerTest {

    @Test
    void setup_databaseConnection_noExceptionsAreThrown() throws SQLException {
        System.out.println("Testing database connection");
        GameDatabaseManager gdbm = new GameDatabaseManager();
        gdbm.setup();
        System.out.println("Connection successful");
    }
}
