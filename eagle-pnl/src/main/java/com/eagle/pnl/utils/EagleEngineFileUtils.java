package com.eagle.pnl.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.eagle.pnl.config.AccountProperties;
import com.eagle.pnl.config.EagleWorkFlowEngineProperties;
import com.eagle.pnl.config.InstrumentProperties;

/**
 * @author ppasupuleti
 *
 */
@Component
public class EagleEngineFileUtils {

	@Autowired
	private EagleWorkFlowEngineProperties engineProperties;

	@Autowired
	private InstrumentProperties instrumentProperties;

	@Autowired
	private AccountProperties accountProperties;

	public String eagleHomeDirectoryPath() {
		ClassPathResource classpathResource = new ClassPathResource(engineProperties.getEagleHomeDirectory());
		return classpathResource.getPath();
	}

	public String getRawDataPath() {
		StringBuilder rawDataPath = new StringBuilder(eagleHomeDirectoryPath());
		rawDataPath.append(File.separator);
		rawDataPath.append(engineProperties.getRawDataDirectory());
		rawDataPath.append(File.separator);
		return rawDataPath.toString();
	}

	public String getInstrumentConfigFile() {
		StringBuilder instrumentConfigFile = new StringBuilder(eagleHomeDirectoryPath());
		instrumentConfigFile.append(File.separator);
		instrumentConfigFile.append(instrumentProperties.getInputFilePath());
		return instrumentConfigFile.toString();
	}

	public String getAccountsConfigFile() {
		StringBuilder instrumentConfigFile = new StringBuilder(eagleHomeDirectoryPath());
		instrumentConfigFile.append(File.separator);
		instrumentConfigFile.append(accountProperties.getInputFilePath());
		return instrumentConfigFile.toString();
	}

	public String getOrderAckPath() {
		StringBuilder orderAckPath = new StringBuilder(eagleHomeDirectoryPath());
		orderAckPath.append(File.separator);
		orderAckPath.append(engineProperties.getOrderAckDirectory());
		orderAckPath.append(File.separator);
		return orderAckPath.toString();
	}

}
