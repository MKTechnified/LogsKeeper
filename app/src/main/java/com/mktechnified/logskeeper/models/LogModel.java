package com.mktechnified.logskeeper.models;

public class LogModel {
    private String id;
    private String logTitle;
    private String logDescription;
    private String logTimestamp;
    private String logType;

    public LogModel() {}

    public LogModel(String logTitle, String logDescription, String logTimestamp, String logType) {
        this.logTitle = logTitle;
        this.logDescription = logDescription;
        this.logTimestamp = logTimestamp;
        this.logType = logType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogTitle() { return logTitle; }
    public void setLogTitle(String logTitle) { this.logTitle = logTitle; }

    public String getLogDescription() { return logDescription; }
    public void setLogDescription(String logDescription) { this.logDescription = logDescription; }

    public String getLogTimestamp() { return logTimestamp; }
    public void setLogTimestamp(String logTimestamp) { this.logTimestamp = logTimestamp; }

    public String getLogType() { return logType; }
    public void setLogType(String logType) { this.logType = logType; }
}
