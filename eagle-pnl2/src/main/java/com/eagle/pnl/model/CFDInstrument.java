package com.eagle.pnl.model;

public class CFDInstrument extends Instrument {

	private static final long serialVersionUID = 1L;

	private InstrumentType instrumentType = InstrumentType.CFD;

	@Override
	public InstrumentType getInstrumentType() {
		return instrumentType;
	}
}
