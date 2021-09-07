package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DAOContext {

    public static void init() {
        DAOManager daoManager = DAOManager.getInstance();
        Connection connection;

        try {
            connection = daoManager.getConnection();
            configDB(connection);
            createTables(connection);
            seedData(connection);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            daoManager.close();
        }
    }

    //   CONFIG

    private static void configDB(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql;
        // Activation of checks FK
        sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }

    private static void clearDB(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql;

        sql = "DELETE FROM card;";
        statement.execute(sql);

        sql = "DELETE FROM column;";
        statement.execute(sql);

        sql = "DELETE FROM board;";
        statement.execute(sql);
    }

    //   CREATE DB

    private static void createTables(Connection connection) throws SQLException {
        String sql;
        Statement stmt = connection.createStatement();

        // SQL statement for board table
        sql = "CREATE TABLE IF NOT EXISTS board ("
                + "	id INTEGER PRIMARY KEY,"
                + "	title TEXT NULL);";
        stmt.execute(sql);

        // SQL statement for column table
        sql = "CREATE TABLE IF NOT EXISTS column ("
                + "	id INTEGER PRIMARY KEY,"
                + "	idBoard INTEGER NOT NULL,"
                + "	title TEXT NULL,"
                + " position INTEGER NOT NULL,"
                + " CONSTRAINT fk_board FOREIGN KEY (idBoard) "
                + " REFERENCES board(id));";
        stmt.execute(sql);

        // SQL statement for card table
        sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	id INTEGER PRIMARY KEY,"
                + "	idColumn INTEGER NOT NULL,"
                + "	title TEXT NULL,"
                + " position INTEGER NOT NULL,"
                + " CONSTRAINT fk_board FOREIGN KEY (idColumn) "
                + " REFERENCES column(id));";
        stmt.execute(sql);

    }

    //   DUMPING DATA

    private static void seedData(Connection connection) throws SQLException {
        clearDB(connection);
        seedBoard(connection);
        seedColumn(connection);
        seedCard(connection);
    }

    private static void seedBoard(Connection connection) throws SQLException {
        String sql = "INSERT INTO board(title) VALUES(?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "Board 1");
        preparedStatement.execute();

        preparedStatement.setString(1, "Board 2");
        preparedStatement.execute();
    }

    private static void seedColumn(Connection connection) throws SQLException {
        String sql = "INSERT INTO column(idBoard, title, position) VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Column 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Column 2");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Column 3");
        preparedStatement.setInt(3,2);
        preparedStatement.execute();

        preparedStatement.setInt(1,2);
        preparedStatement.setString(2, "Column 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,2);
        preparedStatement.setString(2, "Column 2");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();
    }

    private static void seedCard(Connection connection) throws SQLException {
        String sql = "INSERT INTO card(idColumn, title, position) VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Card 1, 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Card 1, 2");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();

        preparedStatement.setInt(1,2);
        preparedStatement.setString(2, "Card 2, 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,3);
        preparedStatement.setString(2, "Card 3, 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,3);
        preparedStatement.setString(2, "Card 3, 2");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();

        preparedStatement.setInt(1,3);
        preparedStatement.setString(2, "Card 3, 3");
        preparedStatement.setInt(3,2);
        preparedStatement.execute();

        preparedStatement.setInt(1,4);
        preparedStatement.setString(2, "Card 1, 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,4);
        preparedStatement.setString(2, "Card 1, 2");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();

        preparedStatement.setInt(1,4);
        preparedStatement.setString(2, "Card 1, 3");
        preparedStatement.setInt(3,2);
        preparedStatement.execute();

        preparedStatement.setInt(1,5);
        preparedStatement.setString(2, "Card 2, 1");
        preparedStatement.setInt(3,0);
        preparedStatement.execute();

        preparedStatement.setInt(1,5);
        preparedStatement.setString(2, "Card 2, 1");
        preparedStatement.setInt(3,1);
        preparedStatement.execute();
    }

}
