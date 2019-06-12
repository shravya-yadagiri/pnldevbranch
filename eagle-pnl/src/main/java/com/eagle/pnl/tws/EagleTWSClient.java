package com.eagle.pnl.tws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eagle.pnl.config.EagleWorkFlowEngineProperties;
import com.eagle.pnl.exception.EagleError;
import com.eagle.pnl.exception.EagleException;
import com.eagle.pnl.model.Instrument;

@Component
public class EagleTWSClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(EagleTWSClient.class);

	private EagleAPI eagleAPI;

	@Autowired
	private EagleWorkFlowEngineProperties eagleEngineProperties;

	public EagleTWSClient() {
	}

	public void setEagleAPI(EagleAPI eagleAPI) {
		this.eagleAPI = eagleAPI;
	}

	public EagleAPI getEagleAPI() {
		return eagleAPI;
	}

	// ----------- Client calls--------

	public void extractHistoricalData(Instrument instrument, int duration, String lastRecordDate) throws EagleException {
	}

	public List<String> getAccounts() {
		try {
			LOGGER.debug("Requsting Getting Accounts: ");
			// Check the IB connection
			
			eagleAPI.checkAndConnect();
			return eagleAPI.getAccounts();
		} catch (EagleException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EagleException(EagleError.FAILED_TO_GET_ACCOUNTS, "", e);
		}
	}

}
