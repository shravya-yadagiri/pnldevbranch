package com.eagle.pnl.tws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ib.controller.ApiConnection.ILogger;

@Component
public class EagleAPILogger implements ILogger {

	private static Logger LOGGER = LoggerFactory.getLogger(EagleAPILogger.class);

	@Override
	public void log(String logMessage) {
	}
}
