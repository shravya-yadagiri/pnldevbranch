package com.eagle.pnl.model;

public class FuturesInstrument extends Instrument {

	private static final long serialVersionUID = 1L;

	private InstrumentType instrumentType = InstrumentType.FUTURES;

	@Override
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

}
