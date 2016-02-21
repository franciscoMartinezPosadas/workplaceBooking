package org.booking.utils;

public class LogUnit {


    private String trace;
    private String service;
    private Integer logLevel;

    public LogUnit(String trace, String service, Integer logLevel) {
        this.trace = trace;
        this.service = service;
        this.logLevel = logLevel;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }
}
