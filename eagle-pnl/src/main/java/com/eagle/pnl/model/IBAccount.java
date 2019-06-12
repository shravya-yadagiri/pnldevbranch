package com.eagle.pnl.model;

import com.eagle.pnl.constants.EagleContractConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IBAccount {

	private static final long serialVersionUID = 1L;

	@JsonProperty(EagleContractConstants.ACCOUNT_IB_HOST)
	private String host;

	@JsonProperty(EagleContractConstants.ACCOUNT_IB_PORT)
	private int port;

	@JsonProperty(EagleContractConstants.ACCOUNT_IB_CLIENTID)
	private int clientId;

	private String accountName;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
