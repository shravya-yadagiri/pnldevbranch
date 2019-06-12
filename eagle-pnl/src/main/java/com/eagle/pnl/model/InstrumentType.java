package com.eagle.pnl.model;

public enum InstrumentType {

	STOCK("STK"), FUTURES("FUT"), OPTION("OPT"), FOREX("CASH"), INDEX("IND"), COMBO("BAG"), CFD("CFD");

	String securityType;

	private InstrumentType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecurityType() {
		return securityType;
	}

	public static InstrumentType getInstrumentType(String type) {
		for (InstrumentType instrumentType : InstrumentType.values()) {
			if (instrumentType.name().equals(type)) {
				return instrumentType;
			}
		}
		return null;
	}
}
