package com.logs.processing.service.impl;

import com.logs.processing.constants.LogState;
import com.logs.processing.model.LoggingDetails;
import com.logs.processing.service.DatabaseService;
import com.logs.processing.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class LogServiceImpl implements LogService {
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    private DatabaseService databaseService;

    public LogServiceImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void processLogs(List<LoggingDetails> loggingDetails) {

        if (Objects.nonNull(loggingDetails) && !loggingDetails.isEmpty()) {
            Map<String, List<LoggingDetails>> loggingDetailsGroupById = loggingDetails.stream().collect(Collectors.groupingBy(LoggingDetails::getId));
            List<LoggingDetails> loggingDetailsListToStore = new ArrayList<>();
            for (Entry<String, List<LoggingDetails>> entry : loggingDetailsGroupById.entrySet()) {
                LoggingDetails saveThis = new LoggingDetails();
                saveThis.setId(entry.getKey());
                List<LoggingDetails> startedList = entry.getValue().stream().filter(logs -> Objects.nonNull(logs.getState()) && logs.getState().equals(LogState.STARTED)).collect(Collectors.toList());
                List<LoggingDetails> finishedList = entry.getValue().stream().filter(logs -> Objects.nonNull(logs.getState()) && logs.getState().equals(LogState.FINISHED)).collect(Collectors.toList());
                if (!startedList.isEmpty() && !finishedList.isEmpty()) {
                    long timeDiff = 0;
                    LoggingDetails startedObject = startedList.get(0);
                    timeDiff = finishedList.get(0).getTimestamp() - startedObject.getTimestamp();
                    saveThis.setHost(startedObject.getHost());
                    saveThis.setType(startedObject.getType());
                    saveThis.setTimestamp(timeDiff);
                    if (timeDiff > 4) {
                        saveThis.setAlert(true);
                    }
                    loggingDetailsListToStore.add(saveThis);
                }
            }

            if (!loggingDetailsListToStore.isEmpty()) {
                try {
                    databaseService.insert(loggingDetailsListToStore);
                } catch (SQLException e) {
                    log.error("Error occurred while saving data into database");
                }
            }

            try {
                databaseService.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
