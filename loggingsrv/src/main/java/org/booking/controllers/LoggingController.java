package org.booking.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.booking.controllers.domain.LogItem;

@RestController
public class LoggingController {

	private static final Logger log = Logger.getLogger(LoggingController.class);

	@RequestMapping(value="/trace", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void trace(@RequestBody LogItem logItem) {
		switch(logItem.getLogLevel()){
			case Priority.DEBUG_INT:
			case Priority.ALL_INT:
				log.debug(logItem.getService() + " - " + logItem.getTrace());
				break;
			case Priority.INFO_INT:
				log.info(logItem.getService() + " - " + logItem.getTrace());
				break;
			case Priority.WARN_INT:
				log.warn(logItem.getService() + " - " + logItem.getTrace());
				break;
			case Priority.ERROR_INT:
				log.error(logItem.getService() + " - " + logItem.getTrace());
				break;
			case Priority.FATAL_INT:
				log.fatal(logItem.getService() + " - " + logItem.getTrace());
				break;
		}
	}

}
