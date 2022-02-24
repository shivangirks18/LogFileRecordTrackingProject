package com.logs.processing.service;

import com.logs.processing.model.LoggingDetails;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    List<LoggingDetails> readFileContent(String path) throws IOException;
}
