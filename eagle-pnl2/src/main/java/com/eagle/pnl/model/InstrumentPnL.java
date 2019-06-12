package com.eagle.pnl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InstrumentPnL {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private String date;
	private String accountName;
	private String ibAccount;
	private String symbol;
	private double realPnl;
	private double unrealPnl;
	private double totalPnl;

	public String getDate() {
		if (date == null) {
			return LocalDateTime.now().format(dateFormat);
		}
		return date;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getIbAccount() {
		return ibAccount;
	}

	public void setIbAccount(String ibAccount) {
		this.ibAccount = ibAccount;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getRealPnl() {
		return realPnl;
	}

	public void setRealPnl(double realPnl) {
		this.realPnl = realPnl;
	}

	public double getUnrealPnl() {
		return unrealPnl;
	}

	public void setUnrealPnl(double unrealPnl) {
		this.unrealPnl = unrealPnl;
	}

	public double getTotalPnl() {
		return totalPnl;
	}

	public void setTotalPnl(double totalPnl) {
		this.totalPnl = totalPnl;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
