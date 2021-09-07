package dao;

import model.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DAOManager {

    private static final String PROPERTIES_FILE = "/dao/dao.properties";
    private static final String PROPERTY_URL = "url";

    private final String url;

    private Connection connection;


    // PRIVATE CLASS SINGLETON + THREAD

    private static class DAOManagerSingleton {

        public static final DAOManager INSTANCE;
        static {
            DAOManager daoManager;
            try {
                daoManager = new DAOManager();
            }
            catch(Exception e) {
                daoManager = null;
            }
            INSTANCE = daoManager;
        }

    }

    //   CONSTRUCTORS

    private DAOManager() throws dao.DAOConfigurationException {
        Properties properties = new Properties();

        InputStream propertiesFile = DAOManager.class.getResourceAsStream(PROPERTIES_FILE);

        if(propertiesFile == null) {
            throw new dao.DAOConfigurationException("Properties file " + PROPERTIES_FILE + " not found.");
        }

        try {
            properties.load(propertiesFile);
            url = properties.getProperty(PROPERTY_URL);
        }
        catch(IOException e) {
            throw new dao.DAOConfigurationException("Impossible to load properties file " + PROPERTIES_FILE, e);
        }
    }

    public static DAOManager getInstance() {
        return DAOManagerSingleton.INSTANCE;
    }


    //   CONNECTION

    public void open() {
        try {
            if(this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(url);
            }
        }
        catch(SQLException e) {
            System.out.println("Fail to open connection : " + e.getMessage());
        }
    }

    public void close() {
        try {
            if(this.connection != null && !this.connection.isClosed()) {
                connection.close();
            }
        }
        catch(SQLException e) {
            System.out.println("Fail to close connection : " + e.getMessage());
        }
    }


    //  FACTORY

    public Connection getConnection() throws SQLException {
        this.open();
        return connection;
    }

    public DAO<Board> getDaoBoard() {
        return new DAOBoard(this);
    }

    public DAOMovable<Column, Board> getDaoColumn() {
        return new DAOColumn(this);
    }

    public DAOMovable<Card, Column> getDaoCard() {
        return new DAOCard(this);
    }

}
