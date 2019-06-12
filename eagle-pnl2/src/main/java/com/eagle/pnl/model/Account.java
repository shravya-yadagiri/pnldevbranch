package com.eagle.pnl.model;

import java.io.Serializable;
import java.util.List;

import com.eagle.pnl.constants.EagleContractConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(EagleContractConstants.ACCOUNT_NAME)
	private String name;

	@JsonProperty(EagleContractConstants.ACCOUNT_PRIMARY)
	private boolean primary;

	@JsonProperty(EagleContractConstants.ACCOUNT_IB)
	private IBAccount iBAccount;

	@JsonProperty(EagleContractConstants.ACCOUNT_INSTUMENTS)
	private List<AccountInstrument> instruments;

	public Account() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public IBAccount getiBAccount() {
		return iBAccount;
	}

	public void setiBAccount(IBAccount iBAccount) {
		this.iBAccount = iBAccount;
	}

	public List<AccountInstrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<AccountInstrument> instruments) {
		this.instruments = instruments;
	}
}