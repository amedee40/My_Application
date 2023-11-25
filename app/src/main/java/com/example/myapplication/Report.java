package com.example.myapplication;

public class Report {

    private String reportId;
    private String reportType;
    private String reportDescription;
    private long timestamp;

    // Required default constructor for Firebase
    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(Report.class)
    }

    public Report(String reportId, String reportType, String reportDescription, long timestamp) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportDescription = reportDescription;
        this.timestamp = timestamp;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
