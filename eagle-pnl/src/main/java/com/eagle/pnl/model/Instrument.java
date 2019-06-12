package com.eagle.pnl.model;

import java.io.Serializable;

import com.eagle.pnl.constants.EagleContractConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Instrument implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty(EagleContractConstants.INSTRUMENT_NAME)
	private String name;

	@JsonProperty(EagleContractConstants.INSTRUMENT_SYMBOL)
	private String symbol;

	@JsonProperty(EagleContractConstants.INSTRUMENT_YAHOO_SYMBOL)
	private String yfsymbol;

	@JsonProperty(EagleContractConstants.INSTRUMENT_EXCHANGE)
	private String exchange;

	@JsonProperty(EagleContractConstants.INSTRUMENT_PRIMARY_EXCHANGE)
	private String primaryExchange;

	@JsonProperty(EagleContractConstants.INSTRUMENT_CURRENCY)
	private String currency = "USD";

	@JsonProperty(EagleContractConstants.INSTRUMENT_EXPIRY)
	private String expiry;

	@JsonProperty(EagleContractConstants.INSTRUMENT_LEVERAGE_FACTOR)
	private int leverageFactor;

	@JsonProperty(EagleContractConstants.INSTRUMENT_PRICE_LIMIT)
	private double priceLimit;

	@JsonProperty(EagleContractConstants.INSTRUMENT_PREDICTION_VALUE)
	private int predictionValue;

	@JsonProperty(EagleContractConstants.INSTRUMENT_PROBABILITY_THRESHOLD)
	private double probabilityThreshold;

	@JsonProperty(EagleContractConstants.INSTRUMENT_HISTORICAL_DATA_PROVIDER)
	private String historicalDataProvider;

	@JsonProperty(EagleContractConstants.INSTRUMENT_STOP_LIMIT_PERCENTAGE)
	private double stopLimitPercentage;

	@JsonProperty(EagleContractConstants.INSTRUMENT_STOP_TRIGGER_PERCENTAGE)
	private double stopTriggerPercentage;

	@JsonProperty(EagleContractConstants.INSTRUMENT_PRICE_ROUNDING_UNIT)
	private double priceRoundingUnit;

	@JsonProperty(EagleContractConstants.INSTRUMENT_IB_HISTORICAL_SYMBOL)
	private String ibHistoricalSymbol;

	@JsonProperty(EagleContractConstants.INSTRUMENT_IB_HISTORICAL_TYPE)
	private InstrumentType ibHistoricalType;

	@JsonProperty(EagleContractConstants.INSTRUMENT_IB_HISTORICAL_EXCHANGE)
	private String ibHistoricalExchange;

	@JsonProperty(EagleContractConstants.INSTRUMENT_MODEL)
	private String model;

	public abstract InstrumentType getInstrumentType();

	public Instrument() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getPrimaryExchange() {
		return primaryExchange;
	}

	public void setPrimaryExchange(String primaryExchange) {
		this.primaryExchange = primaryExchange;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public int getLeverageFactor() {
		return leverageFactor;
	}

	public void setLeverageFactor(int leverageFactor) {
		this.leverageFactor = leverageFactor;
	}

	public double getPriceLimit() {
		return priceLimit;
	}

	public void setPriceLimit(double priceLimit) {
		this.priceLimit = priceLimit;
	}

	public int getPredictionValue() {
		return predictionValue;
	}

	public void setPredictionValue(int predictionValue) {
		this.predictionValue = predictionValue;
	}

	public String getYfsymbol() {
		return yfsymbol;
	}

	public void setYfsymbol(String yfsymbol) {
		this.yfsymbol = yfsymbol;
	}

	public String getHistoricalDataProvider() {
		return historicalDataProvider;
	}

	public void setHistoricalDataProvider(String historicalDataProvider) {
		this.historicalDataProvider = historicalDataProvider;
	}

	public double getStopLimitPercentage() {
		return stopLimitPercentage;
	}

	public void setStopLimitPercentage(double stopLimitPercentage) {
		this.stopLimitPercentage = stopLimitPercentage;
	}

	public double getStopTriggerPercentage() {
		return stopTriggerPercentage;
	}

	public void setStopTriggerPercentage(double stopTriggerPercentage) {
		this.stopTriggerPercentage = stopTriggerPercentage;
	}

	public double getPriceRoundingUnit() {
		return priceRoundingUnit;
	}

	public void setPriceRoundingUnit(double priceRoundingUnit) {
		this.priceRoundingUnit = priceRoundingUnit;
	}

	/**
	 * @return the probabilityThreshold
	 */
	public double getProbabilityThreshold() {
		return probabilityThreshold;
	}

	/**
	 * @param probabilityThreshold
	 *            the probabilityThreshold to set
	 */
	public void setProbabilityThreshold(double probabilityThreshold) {
		this.probabilityThreshold = probabilityThreshold;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getIbHistoricalSymbol() {
		return ibHistoricalSymbol;
	}

	public void setIbHistoricalSymbol(String ibHistoricalSymbol) {
		this.ibHistoricalSymbol = ibHistoricalSymbol;
	}

	public InstrumentType getIbHistoricalType() {
		return ibHistoricalType;
	}

	public void setIbHistoricalType(InstrumentType ibHistoricalType) {
		this.ibHistoricalType = ibHistoricalType;
	}

	public String getIbHistoricalExchange() {
		return ibHistoricalExchange;
	}

	public void setIbHistoricalExchange(String ibHistoricalExchange) {
		this.ibHistoricalExchange = ibHistoricalExchange;
	}

}