package com.eagle.pnl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountPnLInformation {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private String date;
	private String account;
	private String ibaccount;
	private double realPnL;
	private double unrealPnL;
	private double totalPnL;

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

	public double getRealPnL() {
		return realPnL;
	}

	public void setRealPnL(double realPnL) {
		this.realPnL = realPnL;
	}

	public double getUnrealPnL() {
		return unrealPnL;
	}

	public void setUnrealPnL(double unrealPnL) {
		this.unrealPnL = unrealPnL;
	}

	public double getTotalPnL() {
		return totalPnL;
	}

	public void setTotalPnL(double totalPnL) {
		this.totalPnL = totalPnL;
	}

	public String getIbaccount() {
		return ibaccount;
	}

	public void setIbaccount(String ibaccount) {
		this.ibaccount = ibaccount;
	}

}
