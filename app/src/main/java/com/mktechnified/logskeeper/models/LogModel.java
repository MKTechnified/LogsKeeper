package com.mktechnified.logskeeper.models;

public class LogModel {
    private String logTitle;
    private String logDescription;
    private String logTimestamp;

    private String logHashTags, logID;

    public LogModel() {}

    public LogModel(String logTitle, String logDescription, String logHashTags) {
        this.logTitle = logTitle;
        this.logDescription = logDescription;
        this.logHashTags = logHashTags;
        this.logID = this.logTitle + this.logHashTags + this.getLogTimestamp();
    }

    public String getLogHashTags() {
        return logHashTags;
    }

    public void setLogHashTags(String logHashTags) {
        this.logHashTags = logHashTags;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID() {
        this.logID = this.logTitle + this.logHashTags + this.getLogTimestamp();
    }

    public String getLogTitle() { return logTitle; }
    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;

    }

    public String getLogDescription() { return logDescription; }
    public void setLogDescription(String logDescription) { this.logDescription = logDescription; }

    public String getLogTimestamp() { return logTimestamp; }
    public void setLogTimestamp(String logTimestamp) { this.logTimestamp = logTimestamp; }

}
