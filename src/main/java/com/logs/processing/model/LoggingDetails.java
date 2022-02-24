package com.logs.processing.model;

import com.logs.processing.constants.LogState;

public class LoggingDetails {
    private String id;
    private LogState state;
    private String type;
    private String host;
    private long timestamp;
    private boolean alert;

    public LoggingDetails() {
    }

    public LoggingDetails(String id, LogState state, String type, String host, long timestamp, boolean alert) {
        this.id = id;
        this.state = state;
        this.type = type;
        this.host = host;
        this.timestamp = timestamp;
        this.alert = alert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogState getState() {
        return state;
    }

    public void setState(LogState state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
