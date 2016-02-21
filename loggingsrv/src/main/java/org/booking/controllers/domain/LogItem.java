package org.booking.controllers.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogItem {
	
	private Integer logLevel;
	private String service;
	private String trace;

    @JsonCreator
	public LogItem(@JsonProperty("trace") String trace, @JsonProperty("service") String service, @JsonProperty("logLevel") Integer logLevel){
		this.trace = trace;
		this.service = service;
		this.logLevel = logLevel;
	}

    public Integer getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getTrace() {
		return trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}

}