package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    // Inner class Report within the ReportFragment
    public static class Report {

        private String reportId;
        private String reportType;
        private String reportDescription;
        private long timestamp;

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
}
