package com.eagle.pnl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountSummaryInfo {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private String date;
	private String account;
	private String ibAccount;
	private String NetLiquidation;
	private String EquityWithLoanValue;
	private String TotalCashValue;
	private String SMA;
	private String RegTEquity;
	private String currency;

	public String getNetLiquidation() {
		return NetLiquidation;
	}

	public void setNetLiquidation(String netLiquidation) {
		NetLiquidation = netLiquidation;
	}

	public String getEquityWithLoanValue() {
		return EquityWithLoanValue;
	}

	public void setEquityWithLoanValue(String equityWithLoanValue) {
		EquityWithLoanValue = equityWithLoanValue;
	}

	public String getTotalCashValue() {
		return TotalCashValue;
	}

	public void setTotalCashValue(String totalCashValue) {
		TotalCashValue = totalCashValue;
	}

	public String getSMA() {
		return SMA;
	}

	public void setSMA(String sMA) {
		SMA = sMA;
	}

	public String getRegTEquity() {
		return RegTEquity;
	}

	public void setRegTEquity(String regTEquity) {
		RegTEquity = regTEquity;
	}

	public String getDate() {
		if (date == null) {
			return LocalDateTime.now().format(dateFormat);
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCurrency() {
		return currency;
	}

	public String getIbAccount() {
		return ibAccount;
	}

	public void setIbAccount(String ibAccount) {
		this.ibAccount = ibAccount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
