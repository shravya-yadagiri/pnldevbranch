package com.eagle.pnl.config;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "eagle-workflow.accounts")
public class AccountProperties {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountProperties.class);

	@NotNull(message = "No Eagle workflow accounts selected property was provided ")
	private Boolean selected;

	@NotBlank(message = "No Eagle workflow accounts inputFilePath property was provided ")
	private String inputFilePath;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
}
