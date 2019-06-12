package com.eagle.pnl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ppasupuleti
 *
 */
public class AccountPosition {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private String date;
	private String accountName;
	private String ibAccount;
	private String symbol;
	private int position;
	private double marketPrice;
	private double marketValue;
	private double avgCost;
	private double realPnl;
	private double unrealPnl;
	private double totalPnl;
	private String expiry;
	private String contractDescription;
	private String currency;

	public String getDate() {
		if (date == null) {
			return LocalDateTime.now().format(dateFormat);
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public double getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
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

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getContractDescription() {
		return contractDescription;
	}

	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIbAccount() {
		return ibAccount;
	}

	public void setIbAccount(String ibAccount) {
		this.ibAccount = ibAccount;
	}

}
