package com.logs.processing.service;

import com.logs.processing.model.LoggingDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseService {

    Connection createConnection();

    void insert(List<LoggingDetails> loggingDetails) throws SQLException;

    void createTable() throws SQLException;

    void getAll() throws SQLException;
}
