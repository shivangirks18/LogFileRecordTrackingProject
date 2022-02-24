package com.logs.processing.service.impl;

import com.logs.processing.model.LoggingDetails;
import com.logs.processing.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {
    private Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);
    private String url;
    private String username;
    private String password;
    private String driverName;

    private static final String TABLE_LOGS = "LOGS";

    public DatabaseServiceImpl(String url, String username, String password, String driverName) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverName = driverName;
        createTable();
    }

    @Override
    public Connection createConnection() {
        Connection connection = null;

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName(driverName);
            //Creating the connection with HSQLDB
            connection = DriverManager.getConnection(url, username, password);
            if (connection == null) {
                log.debug("Problem with creating connection");
            } else {
                log.debug("Connection created successfully");
            }

        } catch (Exception e) {
          log.error("Error occurred while connecting to DB {}", e.getMessage());
        }
        return connection;
    }

    @Override
    public void insert(List<LoggingDetails> loggingDetails) throws SQLException {
        for (LoggingDetails details : loggingDetails) {
            save(details);
        }
    }

    private void save(LoggingDetails loggingDetails) throws SQLException {
        try (PreparedStatement prepareStatement = createConnection().prepareStatement("INSERT INTO " + TABLE_LOGS + " VALUES (?,?,?,?,?)")) {
            prepareStatement.setString(1, loggingDetails.getId());
            prepareStatement.setInt(2, (int) loggingDetails.getTimestamp());
            prepareStatement.setString(3, loggingDetails.getType());
            prepareStatement.setString(4, loggingDetails.getHost());
            prepareStatement.setBoolean(5, loggingDetails.isAlert());
            if (prepareStatement.executeUpdate() > 0) {
                log.debug("Data saved in database for id {}", loggingDetails.getId());
            } else {
                log.debug("Failed to save data in database for id {}", loggingDetails.getId());
            }
        }
    }

    @Override
    public void createTable() throws SQLException {
        ResultSet resultSet = createConnection().getMetaData().getTables(null, null, "LOGS", null);
        try (Statement statement = createConnection().createStatement()) {
            if (!resultSet.next()) {
                createTable(statement);
            } else {
                statement.executeUpdate("DROP TABLE " + TABLE_LOGS);
                createTable(statement);
                log.info("Table Truncated ");
            }
        }
    }

    private void createTable(Statement statement) throws SQLException {
        int result = 0;
        result = statement.executeUpdate("CREATE TABLE " + TABLE_LOGS + " (ID VARCHAR(50) NOT NULL,DURATION int NOT NULL, TYPE VARCHAR(32),HOST VARCHAR(64) ,ALERT BOOLEAN NOT NULL,PRIMARY KEY(id));");
        if (result == 1)
            log.info("Table created successfully");
        else
            log.info("Failed to create table");
    }

    @Override
    public void getAll() throws SQLException {
        try (Statement statement = createConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT ID,DURATION,TYPE,HOST,ALERT FROM " + TABLE_LOGS);
            log.info("============================ Table data =========================== ");
            while (resultSet.next()) {
                log.info("Id {} DURATION {} TYPE {} HOST {} ALERT {} ", resultSet.getString("ID"), resultSet.getLong("DURATION"), resultSet.getString("TYPE"), resultSet.getString("HOST"), resultSet.getBoolean("ALERT"));
            }
            log.info("============================ Table data End =========================== ");
        }
    }
}
