package com.eagle.pnl.config;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "eagle-workflow.instrument")
public class InstrumentProperties {

	@NotNull(message = "No Eagle workflow instrument selected property was provided ")
	private Boolean selected;

	@NotBlank(message = "No Eagle workflow instrument inputFilePath property was provided ")
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
