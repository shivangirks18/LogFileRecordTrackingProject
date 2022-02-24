package com.logs.processing.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logs.processing.model.LoggingDetails;
import com.logs.processing.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileServiceImpl implements FileService {
    private ObjectMapper mapper;
    private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public FileServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<LoggingDetails> readFileContent(String path) throws IOException {
        log.info("Reading file from path {}", path);
        List<LoggingDetails> loggingDetailsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        	
            if (reader.ready()) {
                reader.lines().forEach(line -> {
                    try {
                        log.debug("Mapping records {} to object", line);
                        loggingDetailsList.add(mapper.readValue(line, LoggingDetails.class));
                        log.debug("Successfully mapped records {} to object", line);
                    } catch (JsonProcessingException e) {
                        log.error("Error occurred while processing record {}", line);
                    }
                });
                log.info("File read successfully from path {}", path);
                
            }
            
        }
    
        return loggingDetailsList; 
    }
}
