package com.logs.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logs.processing.model.LoggingDetails;
import com.logs.processing.service.DatabaseService;
import com.logs.processing.service.FileService;
import com.logs.processing.service.LogService;
import com.logs.processing.service.impl.DatabaseServiceImpl;
import com.logs.processing.service.impl.FileServiceImpl;
import com.logs.processing.service.impl.LogServiceImpl;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LogProcessingApplication {

    public static void main(String[] arg) throws IOException, SQLException {
        try (Scanner sc = new Scanner(arg[0])) {
            String path = sc.next();
            ObjectMapper mapper = new ObjectMapper();
            DatabaseService databaseService = new DatabaseServiceImpl("jdbc:hsqldb:hsql://localhost/testdb", "SA", "", "org.hsqldb.jdbc.JDBCDriver");
            FileService fileService = new FileServiceImpl(mapper);
            LogService logService = new LogServiceImpl(databaseService);

            List<LoggingDetails> loggingDetailsList = fileService.readFileContent(path);
            logService.processLogs(loggingDetailsList);
        }
    }
}
