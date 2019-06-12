package com.eagle.pnl.config;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "eagle-workflow")
public class EagleWorkFlowEngineProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(EagleWorkFlowEngineProperties.class);

	@NotBlank(message = "No Eagle workflow Job schedule property was provided ")
	private String schedule;

	@NotBlank(message = "No Eagle workflow eagleHomeDirectory property was provided ")
	private String eagleHomeDirectory;

	@NotBlank(message = "No Eagle workflow rawDataDirectory property was provided ")
	private String rawDataDirectory;

	@NotBlank(message = "No Eagle workflow rawDataFileType property was provided ")
	private String rawDataFileType;

	@NotBlank(message = "No Eagle workflow orderAckDirectory property was provided ")
	private String orderAckDirectory;

	@NotNull(message = "No Eagle workflow instrument properties were provided ")
	private InstrumentProperties instrument;

	@NotNull(message = "No Eagle workflow account properties were provided ")
	private AccountProperties accounts;

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public InstrumentProperties getInstrument() {
		return instrument;
	}

	public String getOrderAckDirectory() {
		return orderAckDirectory;
	}

	public void setOrderAckDirectory(String orderAckDirectory) {
		this.orderAckDirectory = orderAckDirectory;
	}

	public void setInstrument(InstrumentProperties instrument) {
		this.instrument = instrument;
	}

	public String getEagleHomeDirectory() {
		return eagleHomeDirectory;
	}

	public void setEagleHomeDirectory(String eagleHomeDirectory) {
		this.eagleHomeDirectory = eagleHomeDirectory;
	}

	public String getRawDataDirectory() {
		return rawDataDirectory;
	}

	public void setRawDataDirectory(String rawDataDirectory) {
		this.rawDataDirectory = rawDataDirectory;
	}

	public String getRawDataFileType() {
		return rawDataFileType;
	}

	public void setRawDataFileType(String rawDataFileType) {
		this.rawDataFileType = rawDataFileType;
	}

	public AccountProperties getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountProperties accounts) {
		this.accounts = accounts;
	}

}
