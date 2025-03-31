package com.mktechnified.logskeeper.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LogModel {
    private String logTitle;
    private String logDescription;
    private String logHashTags;
    private String logID;

    @ServerTimestamp
    private Timestamp logTimestamp;

    // Empty constructor for Firestore
    public LogModel() {}

    public LogModel(String logTitle, String logDescription, String logHashTags) {
        this.logTitle = logTitle;
        this.logDescription = logDescription;
        this.logHashTags = logHashTags;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getLogHashTags() {
        return logHashTags;
    }

    public void setLogHashTags(String logHashTags) {
        this.logHashTags = logHashTags;
    }

    public Timestamp getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Timestamp logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String generateLogID() {
        if (logTimestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedTimestamp = sdf.format(logTimestamp.toDate());
            return logTitle + " " + logHashTags + " " + formattedTimestamp;
        }
        return logTitle + " " + logHashTags;
    }
}
