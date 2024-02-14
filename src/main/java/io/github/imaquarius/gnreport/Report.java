package io.github.imaquarius.gnreport;

public class Report {
    private final String reporter;
    private final String reported;
    private final String reason;
    private final long timestamp;

    public Report(String reporter, String reported, String reason, long timestamp) {
        this.reporter = reporter;
        this.reported = reported;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReported() {
        return reported;
    }

    public String getReason() {
        return reason;
    }

    public long getTimestamp() {
        return timestamp;
    }
}