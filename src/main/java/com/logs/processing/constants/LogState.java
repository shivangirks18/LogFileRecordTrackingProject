package com.logs.processing.constants;

public enum LogState {

    STARTED("Started"),FINISHED("Finished");

    private String value;

    public String getValue() {
        return value;
    }

    LogState(String value) {
        this.value = value;
    }
}
