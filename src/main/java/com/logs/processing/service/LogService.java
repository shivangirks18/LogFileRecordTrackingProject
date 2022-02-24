package com.logs.processing.service;

import com.logs.processing.model.LoggingDetails;

import java.util.List;

public interface LogService {
    void processLogs(List<LoggingDetails> loggingDetails);
}
